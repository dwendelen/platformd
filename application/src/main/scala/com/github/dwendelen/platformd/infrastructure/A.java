package com.github.dwendelen.platformd.infrastructure;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by xtrit on 12/01/17.
 */
//@Component
public class A {
@Autowired
private KafkaConsumer<String, String> kafkaConsumer;
@Autowired
private KafkaProducer<String, String> kafkaProducer;

@Scheduled(fixedRate = 1000)
    public void stuffc() {
        kafkaConsumer.subscribe(newArrayList("top1", "top2"));
    ConsumerRecords<String, String> poll = kafkaConsumer.poll(1000);
    for (ConsumerRecord<String, String> stringStringConsumerRecord : poll) {
        System.out.println(stringStringConsumerRecord);
    }
}

@Scheduled(fixedRate = 1000)
    public void stuffp() {
        kafkaProducer.send(new ProducerRecord<String, String>("top1", 4, UUID.randomUUID().toString(), "oeuaaoeuao"));
        kafkaProducer.send(new ProducerRecord<String, String>("top1", UUID.randomUUID().toString(), "oseuthaosueahsn"));
        kafkaProducer.send(new ProducerRecord<String, String>("top2", UUID.randomUUID().toString(), "tnfgcedeodeaiPP"));
}
}
