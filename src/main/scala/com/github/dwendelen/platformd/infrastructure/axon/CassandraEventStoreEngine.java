package com.github.dwendelen.platformd.infrastructure.axon;

import com.datastax.driver.core.*;
import com.datastax.driver.core.querybuilder.Batch;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.google.common.collect.FluentIterable;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.TrackedEventMessage;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.axonframework.serialization.SerializedObject;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.SimpleSerializedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class CassandraEventStoreEngine implements EventStorageEngine {
    public static int MAX_PARTITION_SIZE = 8 * 1024;
    private static int NB_OF_BITS_TO_SHIFT = 13;

    public static final String TABLE_AGGREGATE_EVENTS = "domain_event";
    public static final String TABLE_SNAPSHOTS = "snapshot";

    public static final String AGGREGATE_ID = "aggregateId";
    public static final String PARTITION = "partition";
    public static final String SEQUENCE_NUMBER = "sequenceNb";
    public static final String TIMESTAMP = "timestamp";
    public static final String META_DATA = "metadata";
    public static final String PAYLOAD = "payload";
    public static final String PAYLOAD_TYPE = "payloadType";
    public static final String MESSAGE_ID = "messageId";
    public static final String AGGREGATE_TYPE = "aggregateType";

    private Session session;
    private Serializer serializer;

    @Autowired
    public CassandraEventStoreEngine(Session session, Serializer serializer) {
        this.session = session;
        this.serializer = serializer;
    }

    @Override
    public void appendEvents(List<? extends EventMessage<?>> events) {
        FluentIterable<DomainEventMessage> domainEvents = FluentIterable.from(events)
                .filter(DomainEventMessage.class);

        long partition = calculatePartitionLastEvent(domainEvents);

        Batch batch = QueryBuilder.batch();
        domainEvents.forEach(event -> {
            String meta = serializer.serialize(event.getMetaData(), String.class).getData();
            SerializedObject<String> payload = serializer.serialize(event.getPayload(), String.class);

            batch.add(QueryBuilder.insertInto(TABLE_AGGREGATE_EVENTS)
                    .value(AGGREGATE_ID, event.getAggregateIdentifier())
                    .value(PARTITION, partition)
                    .value(SEQUENCE_NUMBER, event.getSequenceNumber())
                    .value(TIMESTAMP, event.getTimestamp().toEpochMilli())
                    .value(META_DATA, meta)
                    .value(PAYLOAD, payload.getData())
                    .value(PAYLOAD_TYPE, payload.getType().getName())
                    .value(MESSAGE_ID, event.getIdentifier())
                    .value(AGGREGATE_TYPE, event.getType())
                    .ifNotExists()
            );
        });

        session.execute(batch);
    }

    @Override
    public DomainEventStream readEvents(String aggregateIdentifier, long firstSequenceNumber) {
        long firstPartition = calculatePartitionLastEvent(firstSequenceNumber);

        Function<Long, ResultSetFuture> queryFunction =
                partition -> session.executeAsync(
                        QueryBuilder.select().from(TABLE_AGGREGATE_EVENTS)
                                .where(QueryBuilder.eq(AGGREGATE_ID, aggregateIdentifier))
                                .and(QueryBuilder.eq(PARTITION, partition))
                                .and(QueryBuilder.gte(SEQUENCE_NUMBER, firstSequenceNumber))
                );
        return new CassandraDomainEventStream(queryFunction, this::mapDomainEventMessage, firstPartition);
    }

    private long calculatePartitionLastEvent(FluentIterable<DomainEventMessage> events) {
        return events
                .last()
                .transform(DomainEventMessage::getSequenceNumber)
                .transform(this::calculatePartitionLastEvent)
                .or(0L);
    }

    private long calculatePartitionLastEvent(long seqNb) {
        return seqNb >> NB_OF_BITS_TO_SHIFT;
    }

    @Override
    public void storeSnapshot(DomainEventMessage snapshot) {
        String meta = serializer.serialize(snapshot.getMetaData(), String.class).getData();
        SerializedObject<String> payload = serializer.serialize(snapshot.getPayload(), String.class);

        Statement query = QueryBuilder.insertInto(TABLE_SNAPSHOTS)
                .value(AGGREGATE_ID, snapshot.getAggregateIdentifier())
                .value(SEQUENCE_NUMBER, snapshot.getSequenceNumber())
                .value(TIMESTAMP, snapshot.getTimestamp())
                .value(META_DATA, meta)
                .value(PAYLOAD, payload.getData())
                .value(PAYLOAD_TYPE, payload.getType().getName())
                .value(MESSAGE_ID, snapshot.getIdentifier())
                .value(AGGREGATE_TYPE, snapshot.getType());

        session.execute(query);
    }

    @Override
    public Optional<DomainEventMessage<?>> readSnapshot(String aggregateIdentifier) {
        ResultSet result = session.execute(
                QueryBuilder.select().from(TABLE_SNAPSHOTS)
                    .where(QueryBuilder.eq(AGGREGATE_ID, aggregateIdentifier))
        );

        return StreamSupport.stream(result.spliterator(), false)
                .<DomainEventMessage<?>>map(this::mapDomainEventMessage)
                .findFirst();
    }

    private DomainEventMessage<?> mapDomainEventMessage(Row row) {
        Object payload = deserialise(row.getString(PAYLOAD), row.getString(PAYLOAD_TYPE));
        Map<String, ?> meta = deserialise(row.getString(META_DATA), "java.util.Map");

        return new GenericDomainEventMessage<>(
                row.getString(AGGREGATE_TYPE),
                row.getString(AGGREGATE_ID),
                row.getLong(SEQUENCE_NUMBER),
                payload,
                meta,
                row.getString(MESSAGE_ID),
                row.get(TIMESTAMP, Instant.class)
        );
    }

    public <T> T deserialise(String object, String type) {
        return serializer.deserialize(new SimpleSerializedObject<>(object, String.class, type, null));
    }

    @PostConstruct
    public void createTables() {
        session.execute(
                SchemaBuilder.createTable(TABLE_AGGREGATE_EVENTS)
                        .addPartitionKey(AGGREGATE_ID, DataType.text())
                        .addPartitionKey(PARTITION, DataType.bigint())
                        .addClusteringColumn(SEQUENCE_NUMBER, DataType.bigint())
                        .addColumn(TIMESTAMP, DataType.timestamp())
                        .addColumn(META_DATA, DataType.text())
                        .addColumn(PAYLOAD, DataType.text())
                        .addColumn(PAYLOAD_TYPE, DataType.text())
                        .addColumn(MESSAGE_ID, DataType.text())
                        .addColumn(AGGREGATE_TYPE, DataType.text())
                        .ifNotExists()
        );

        session.execute(
                SchemaBuilder.createTable(TABLE_SNAPSHOTS)
                        .addPartitionKey(AGGREGATE_ID, DataType.text())
                        .addColumn(SEQUENCE_NUMBER, DataType.bigint())
                        .addColumn(TIMESTAMP, DataType.timestamp())
                        .addColumn(META_DATA, DataType.text())
                        .addColumn(PAYLOAD, DataType.text())
                        .addColumn(PAYLOAD_TYPE, DataType.text())
                        .addColumn(MESSAGE_ID, DataType.text())
                        .addColumn(AGGREGATE_TYPE, DataType.text())
                        .ifNotExists()
        );
    }

    public void clearTables() {
        session.execute(QueryBuilder.truncate(TABLE_AGGREGATE_EVENTS));
        session.execute(QueryBuilder.truncate(TABLE_SNAPSHOTS));
    }

    @Override
    public Stream<? extends TrackedEventMessage<?>> readEvents(TrackingToken trackingToken, boolean mayBlock) {
        throw new UnsupportedOperationException();
    }
}