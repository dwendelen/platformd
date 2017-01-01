package com.github.dwendelen.platformd.infrastructure.engine;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.RegularStatement;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventhandling.TrackedEventMessage;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

//@Component
public class CassandraEventStoreEngine implements EventStorageEngine {
    @Autowired
    private Session session;

    @Override
    public void appendEvents(List<? extends EventMessage<?>> events) {
        EventMessage<?> eventMessage = events.get(0);

        /*PreparedStatement insertStatement = session.prepare(
                QueryBuilder.insertInto("event")
                        .value("type", "?")
                        .value("id", "?")
                        .value("segNb", "?")
                        .value("seqNb", "?")
                        .value(""));*/
    }

    @Override
    public void storeSnapshot(DomainEventMessage<?> snapshot) {

    }

    @Override
    public Stream<? extends TrackedEventMessage<?>> readEvents(TrackingToken trackingToken, boolean mayBlock) {
        return null;
    }

    @Override
    public DomainEventStream readEvents(String aggregateIdentifier, long firstSequenceNumber) {
        return null;
    }

    @Override
    public Optional<DomainEventMessage<?>> readSnapshot(String aggregateIdentifier) {
        return Optional.empty();
    }
}
