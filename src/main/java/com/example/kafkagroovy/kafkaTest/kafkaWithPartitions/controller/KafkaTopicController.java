package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * topic 생성 controller
 */
@RequiredArgsConstructor
@RestController
public class KafkaTopicController {


    private final KafkaAdmin kafkaAdmin;

    /**
     * topic 생성 function
     * @param topicName
     * @return success OK , fail Error
     */
    @GetMapping("/createTopic")
    private String defaultTopic(@RequestParam("TopicName") String topicName){
        try {
            kafkaAdmin.createOrModifyTopics(TopicBuilder.name(topicName)
                    .build());
            return "OK";
        } catch (Exception e) {
            System.out.println("Exception");
            return "Error";
        }
    }

}
