package com.github.dwendelen.platformd.infrastructure.axon;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventhandling.tokenstore.UnableToClaimTokenException;
import org.axonframework.eventsourcing.eventstore.TrackingToken;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created by xtrit on 1/01/17.
 */
public class CassandraTokenStore implements TokenStore {
    public static final String TABLE_TRACKING_TOKEN = "tracking_token";
    @Autowired
    private Session session;

    @PostConstruct
    public void createTables() {
        session.execute(
                SchemaBuilder.createTable(TABLE_TRACKING_TOKEN)
        );
    }

    @Override
    public void storeToken(TrackingToken token, String processorName, int segment) throws UnableToClaimTokenException {
        if (!(token instanceof CassandraToken)) {
            throw new UnsupportedOperationException("Only Cassandra tracking tokens are supported");
        }

        CassandraToken cToken = (CassandraToken)token;
    }

    @Override
    public TrackingToken fetchToken(String processorName, int segment) throws UnableToClaimTokenException {
        return null;
    }

    @Override
    public void releaseClaim(String processorName, int segment) {

    }
}
