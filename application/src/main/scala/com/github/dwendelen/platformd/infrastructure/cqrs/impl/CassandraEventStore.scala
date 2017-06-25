package com.github.dwendelen.platformd.infrastructure.cqrs.impl

import java.util.UUID
import java.util.concurrent.Executor

import com.datastax.driver.core._
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.mapping.annotations.{ClusteringColumn, Column, PartitionKey, Table}
import com.datastax.driver.mapping.{Mapper, MappingManager, Result}
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.github.dwendelen.platformd.core.account.RejectMoneyMade
import com.github.dwendelen.platformd.infrastructure.cqrs.Aggregate
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import rx.lang.scala.Observable

import scala.annotation.meta.field
import scala.beans.BeanProperty

object CassandraEventStore {
    val MAX_PARTITION_SIZE = 8 * 1024
    val NB_OF_BITS_TO_SHIFT = 13
}

@Component
class CassandraEventStore(session: Session, @Qualifier("cassandraObjectMapper") objectMapper: ObjectMapper, executor: Executor) {
    private val mapperManager = new MappingManager(session)
    private val mapper: Mapper[Event] = mapperManager.mapper(classOf[Event])

    def store(aggregate: UUID, events: List[EventWrapper]): Unit = {
        val bucket = calculatePartitionLastEvent(events)
        val batch = events
            .map(event => new Event(aggregate,
                bucket,
                event.sequenceNumber,
                objectMapper.writeValueAsString(event.event)
            ))
            .foldLeft(QueryBuilder.batch())((batch, event) => batch.add(QueryBuilder.insertInto("event")
                .value("aggregate_id", event.aggregateId)
                .value("payload", event.payload)
                .value("sequencenumber", event.sequenceNumber)
                .value("bucket", event.bucket)
            ))
        session.execute(batch)
    }

    private def calculatePartitionLastEvent(events: List[EventWrapper]): Long = {
        val seq = events.lastOption
            .map(e => e.sequenceNumber)
            .getOrElse(0L)
        calculatePartitionLastEvent(seq)
    }

    private def calculatePartitionLastEvent(seqNb: Long): Long =
        seqNb >> CassandraEventStore.NB_OF_BITS_TO_SHIFT

    def find[T <: Aggregate[T]](aggregate: UUID, clazz: Class[T]): AggregateWrapper[T] = {
        val initialObject = new AggregateWrapper(-1, clazz.newInstance())
        applyBuckets(aggregate, 0, initialObject)
    }

    private def applyBuckets[T <: Aggregate[T]](aggregateId: UUID, bucket: Long, aggregate: AggregateWrapper[T]): AggregateWrapper[T] = {
        val resultSet = session.execute(queryBucket(aggregateId, bucket))
        val eventsInBucket = mapper.map(resultSet)
        val aggregateAfterNewEvents = applyEvents(eventsInBucket, aggregate)

        if(aggregateAfterNewEvents.lastSequenceNumber == aggregate.lastSequenceNumber) {
            aggregateAfterNewEvents
        } else {
            applyBuckets(aggregateId, bucket + 1, aggregateAfterNewEvents)
        }
    }

    private def applyEvents[T <: Aggregate[T]](resultSet: Result[Event], aggregateWrapper: AggregateWrapper[T]): AggregateWrapper[T] = {
        if (resultSet.isExhausted) {
            aggregateWrapper
        } else {
            val cassandraEvent: Event = resultSet.one()
            val event = objectMapper.readValue(cassandraEvent.payload, classOf[Any])
            val newAggregate = aggregateWrapper.aggregate.applyEvent(event)
            applyEvents(resultSet, new AggregateWrapper[T](cassandraEvent.sequenceNumber, newAggregate))
        }
    }

    private def queryBucket(aggregate: UUID, bucket: Long): RegularStatement =
        QueryBuilder.select().from("event")
            .where(QueryBuilder.eq("aggregate_id", aggregate))
            .and(QueryBuilder.eq("bucket", bucket))
}

@Table(name = "event")
class Event(@(PartitionKey @field)(value = 0)
            @(Column @field)(name = "aggregate_id")
            @BeanProperty
            var aggregateId: UUID,
            @(PartitionKey @field)(value = 1)
            @(Column @field)(name = "bucket")
            @BeanProperty
            var bucket: Long,
            @ClusteringColumn
            @(Column @field)(name = "sequenceNumber")
            @BeanProperty
            var sequenceNumber: Long,
            @(Column @field)(name = "payload")
            @BeanProperty
            var payload: String) {
    def this() = this(null, 0, 0, null)
}

class AggregateWrapper[T](
                             val lastSequenceNumber: Long,
                             val aggregate: T
                         )

class BucketWrapper[T](
                          val bucket: Long,
                          val observable: Observable[T]
                      )

