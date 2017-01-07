package com.github.dwendelen.platformd.infrastructure.axon;

import org.axonframework.eventsourcing.eventstore.TrackingToken;

public class CassandraToken implements TrackingToken {
    private int segment;
}
