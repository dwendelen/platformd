package com.github.dwendelen.platformd.infrastructure;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xtrit on 12/01/17.
 */
@Configuration
public class KafkaConfig {
    @Bean
    public KafkaConsumer<String, String> kafkaConsumer() {
        Map<String, Object> conf = new HashMap<>();
        conf.put("bootstrap.servers", "localhost:9092");
        conf.put("group.id", "g1");
        conf.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        conf.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return new KafkaConsumer<>(conf);
    }

    @Bean
    public KafkaProducer<String, String> prod() {
        Map<String, Object> conf = new HashMap<>();
        conf.put("bootstrap.servers", "localhost:9092");
        conf.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        conf.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
return new KafkaProducer<String, String>(conf);
    }
}
