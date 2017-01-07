package com.github.dwendelen.platformd.infrastructure;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
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
                .register(InstantCodec.instance);
        return cluster.connect("platformd");
    }
}
