package com.example.kafkagroovy.kafkaTest.kafkaBasic;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

//@Service
public class KafkaConsumer {

    @KafkaListener(topics = "choi2", groupId = "consumerGroup")
    public void consumer(String message) throws IOException {
        System.out.println(String.format("Consumed message : %s", message));

    }
}
