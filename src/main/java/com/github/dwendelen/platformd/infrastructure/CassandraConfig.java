package com.github.dwendelen.platformd.infrastructure;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalDateCodec;
import com.datastax.driver.extras.codecs.jdk8.LocalTimeCodec;
import com.datastax.driver.extras.codecs.jdk8.OptionalCodec;
import com.datastax.driver.mapping.MappingManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CassandraConfig {
    @Bean
    public Session session() {
        Cluster cluster = Cluster.builder()
                .addContactPoint("localhost")
                .build();

        cluster.getConfiguration().getCodecRegistry()
                .register(InstantCodec.instance)
                .register(LocalDateCodec.instance)
                .register(LocalTimeCodec.instance);
        return cluster.connect("platformd");
    }

    @Bean
    public MappingManager mappingManager(Session session) {
        return new MappingManager(session);
    }
}
