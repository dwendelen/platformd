package com.github.dwendelen.platformd.infrastructure.axon;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.GenericDomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.github.dwendelen.platformd.infrastructure.axon.CassandraEventStoreEngine.MAX_PARTITION_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

public class CassandraEventStoreEngineIT {
    public static final String AGGREGATE_TYPE = "type";
    public static final String AGGREGATE_IDENTIFIER = "aggId";
    public static final Map<String, Object> META_1 = new HashMap<>();
    public static final String MESSAGE_ID = "messageid1";
    public static final Instant INSTANT = Instant.now();
    static {
        META_1.put("m1", "meta1");
        META_1.put("m2", 2);
    }

    private CassandraEventStoreEngine cassandraEventStoreEngine;


    private static Session session;
    private static boolean initialised = false;

    @Before
    public void setup() {
        if(!initialised) {
            initialised = true;
            session = createAndConfigureSession();
        }

        Serializer serializer = new JacksonSerializer();

        cassandraEventStoreEngine = new CassandraEventStoreEngine(session, serializer);
        cassandraEventStoreEngine.createTables();
        cassandraEventStoreEngine.clearTables();
    }

    @Test
    public void testAggregateEvents() throws Exception {
        GenericDomainEventMessage<TObj> m1 = new GenericDomainEventMessage<>(AGGREGATE_TYPE, AGGREGATE_IDENTIFIER, 0, new TObj(), META_1, MESSAGE_ID, INSTANT);

        Instant instant2 = INSTANT.plus(1, ChronoUnit.SECONDS);
        GenericDomainEventMessage<TObj> m2 = new GenericDomainEventMessage<>(AGGREGATE_TYPE, AGGREGATE_IDENTIFIER, 1, new TObj(), new HashMap<>(), "messageid2", instant2);

        cassandraEventStoreEngine.appendEvents(m1, m2);

        DomainEventStream stream = cassandraEventStoreEngine.readEvents(AGGREGATE_IDENTIFIER);
        DomainEventMessage<?> event1 = stream.next();
        DomainEventMessage<?> event2 = stream.next();

        assertThat(event1.getAggregateIdentifier()).isEqualTo(AGGREGATE_IDENTIFIER);
        assertThat(event1.getType()).isEqualTo(AGGREGATE_TYPE);
        assertThat(event1.getSequenceNumber()).isEqualTo(0);
        assertThat(event1.getIdentifier()).isEqualTo(MESSAGE_ID);
        assertThat(event1.getTimestamp()).isEqualTo(INSTANT);
        assertThat(event1.getMetaData()).isEqualTo(META_1);
        assertThat(event1.getPayload()).isEqualToComparingFieldByField(new TObj());

        assertThat(event2.getSequenceNumber()).isEqualTo(1);

        DomainEventStream domainEventStream = cassandraEventStoreEngine.readEvents(AGGREGATE_IDENTIFIER, 1);
        assertThat(domainEventStream).hasSize(1);
    }

    @Test
    public void testSnapshot() throws Exception {
        GenericDomainEventMessage<TObj> snapshot = new GenericDomainEventMessage<>(AGGREGATE_TYPE, AGGREGATE_IDENTIFIER, 0, new TObj(), META_1, MESSAGE_ID, INSTANT);
        cassandraEventStoreEngine.storeSnapshot(snapshot);

        Optional<DomainEventMessage<?>> maybeSnapshot = cassandraEventStoreEngine.readSnapshot(AGGREGATE_IDENTIFIER);
        DomainEventMessage<?> actual = maybeSnapshot.get();

        assertThat(actual.getAggregateIdentifier()).isEqualTo(AGGREGATE_IDENTIFIER);
        assertThat(actual.getType()).isEqualTo(AGGREGATE_TYPE);
        assertThat(actual.getSequenceNumber()).isEqualTo(0);
        assertThat(actual.getIdentifier()).isEqualTo(MESSAGE_ID);
        assertThat(actual.getTimestamp()).isEqualTo(INSTANT);
        assertThat(actual.getMetaData()).isEqualTo(META_1);
        assertThat(actual.getPayload()).isEqualToComparingFieldByField(new TObj());
    }

    @Test
    public void testMultiplePartitions() throws Exception {
        List<GenericDomainEventMessage<TObj>> fillers = IntStream.range(0, MAX_PARTITION_SIZE - 1)
                .mapToObj(i -> new GenericDomainEventMessage<>(
                        AGGREGATE_TYPE, AGGREGATE_IDENTIFIER, i, new TObj(), new HashMap<>(),
                        UUID.randomUUID().toString(), Instant.now())
                )
                .collect(Collectors.toList());
        cassandraEventStoreEngine.appendEvents(fillers);

        List<GenericDomainEventMessage<TObj>> spillers = IntStream.range(MAX_PARTITION_SIZE - 1, MAX_PARTITION_SIZE + 1)
                .mapToObj(i -> new GenericDomainEventMessage<>(
                        AGGREGATE_TYPE, AGGREGATE_IDENTIFIER, i, new TObj(), new HashMap<>(),
                        UUID.randomUUID().toString(), Instant.now())
                )
                .collect(Collectors.toList());
        cassandraEventStoreEngine.appendEvents(spillers);

        ResultSet result = session.execute(QueryBuilder.select().from(CassandraEventStoreEngine.TABLE_AGGREGATE_EVENTS)
                .where(QueryBuilder.eq(CassandraEventStoreEngine.AGGREGATE_ID, AGGREGATE_IDENTIFIER))
                .and(QueryBuilder.eq(CassandraEventStoreEngine.PARTITION, 1)));

        assertThat(result.all()).hasSize(2);

        DomainEventStream domainEventStream1 = cassandraEventStoreEngine.readEvents(AGGREGATE_IDENTIFIER);
        assertThat(domainEventStream1).hasSize(MAX_PARTITION_SIZE + 1);

        DomainEventStream domainEventStream2 = cassandraEventStoreEngine.readEvents(AGGREGATE_IDENTIFIER, MAX_PARTITION_SIZE - 1);
        assertThat(domainEventStream2).hasSize(2);
    }

    @Test
    public void testNoEvents() throws Exception {
        DomainEventStream actual = cassandraEventStoreEngine.readEvents("bla", 0);
        assertThat(actual).isEmpty();
    }

    @Test
    public void testNoSnapshot() {
        Optional<DomainEventMessage<?>> noSnapshot = cassandraEventStoreEngine.readSnapshot("blabla");
        assertThat(noSnapshot).isEmpty();
    }

    private Session createAndConfigureSession() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("localhost")
                .build();
        cluster.getConfiguration().getCodecRegistry().register(InstantCodec.instance);
        return cluster.connect("test");
    }


    private static class TObj {
        public String field1 = "field1";
        public String field2 = "field2";

        public String getField1() {
            return field1;
        }

        public String getField2() {
            return field2;
        }
    }
}