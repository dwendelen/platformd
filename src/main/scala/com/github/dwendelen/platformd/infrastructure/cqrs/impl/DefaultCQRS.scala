package com.github.dwendelen.platformd.infrastructure.impl

import java.util.UUID

import com.datastax.driver.core.Session
import com.github.dwendelen.platformd.infrastructure.cqrs._
import com.github.dwendelen.platformd.infrastructure.axon.CassandraEventStoreEngine
import com.github.dwendelen.platformd.infrastructure.cassandra.CassandraEventStore
import com.github.dwendelen.platformd.infrastructure.cqrs.impl.EventWrapper
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.GenericDomainEventMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import rx.lang.scala.Observable

import scala.reflect.ClassTag

@Component
class DefaultCQRS(cassandraEventStore: CassandraEventStore) extends CQRS {
    var listeners: List[Listener] = List()

    override def execute[T <: Aggregate[T], R](identifier: UUID, command: Any, clazz: Class[T]): CommandResult = {
        val aggWrapper = cassandraEventStore.find(identifier, clazz)

        val result = aggWrapper.aggregate.handle(command)

        storeNewEvents(identifier, result.events, aggWrapper.lastSequenceNumber + 1)
        broadcast(result.events, listeners)

        result.result
    }

    private def storeNewEvents(identifier: UUID, events: List[Any], firstSequenceNumber: Long): Unit = {
        val sequenceNumbers = firstSequenceNumber to Int.MaxValue - 1
        val eventWrappers: List[EventWrapper] = events.zip(sequenceNumbers).map(zipped => new EventWrapper(zipped._2, zipped._1))
        cassandraEventStore.store(identifier, eventWrappers)
    }

    override def registerListener(listener: Listener) = {
        listeners = listener :: listeners
    }

    private def broadcast(events: List[Any], listeners: List[Listener]): Unit = listeners match {
        case Nil =>
        case listener :: tail =>
            try {
                sendToListener(listener, events)
            } catch {
                case e: Exception => e.printStackTrace()
            }

            broadcast(events, tail)
    }

    private def sendToListener(listener: Listener, events: List[Any]): Unit = events match {
        case Nil =>
        case event :: tail =>
            listener.handle(event)
            sendToListener(listener, tail)
    }
}
