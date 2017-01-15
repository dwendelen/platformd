package com.github.dwendelen.platformd.infrastructure.axon;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Row;
import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;

import java.util.function.Function;

public class CassandraDomainEventStream implements DomainEventStream {
    private long nextPartition;

    private ResultSetFuture upcoming;
    private ResultSet current;
    private DomainEventMessage<?> peek;

    private Function<Long, ResultSetFuture> query;
    private Function<Row, DomainEventMessage<?>> mapper;

    private long lastSeqNumber = 0;

    public CassandraDomainEventStream(Function<Long, ResultSetFuture> query, Function<Row, DomainEventMessage<?>> mapper, long firstPartition) {
        this.nextPartition = firstPartition;
        this.query = query;
        this.mapper = mapper;

        init();
    }

    private void init() {
        initCurrent();
        //If first result set is empty -> try again
        if(current == null) {
            initCurrent();
        }

        //If there are no results, then we can not fetch next peek
        if(current != null) {
            fetchNextPeek();
        }
    }

    private void initCurrent() {
        fetchNextUpcoming();
        fetchNextCurrent();
    }

    @Override
    public boolean hasNext() {
        return peek != null;
    }

    @Override
    public DomainEventMessage<?> next() {
        DomainEventMessage<?> next = peek;
        lastSeqNumber = peek.getSequenceNumber();
        fetchNextPeek();
        return next;
    }

    @Override
    public DomainEventMessage<?> peek() {
        return peek;
    }

    @Override
    public Long getLastSequenceNumber() {
        return lastSeqNumber;
    }

    private void fetchNextPeek() {
        if(current.isExhausted()) {
            fetchNextCurrent();
        }

        if(current == null) {
            peek = null;
        } else {
            peek = mapper.apply(current.one());
        }
    }

    private void fetchNextCurrent() {
        ResultSet nextCurrent = upcoming.getUninterruptibly();
        if(nextCurrent.isExhausted()) {
            current = null;
            return;
        }
        current = nextCurrent;
        fetchNextUpcoming();
    }

    private void fetchNextUpcoming() {
        upcoming = query.apply(nextPartition);
        nextPartition++;
    }
}
