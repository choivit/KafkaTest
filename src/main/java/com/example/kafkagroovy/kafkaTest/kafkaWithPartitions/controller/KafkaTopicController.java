package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.controller;

import com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    private String topicN;
    /**
     * topic 생성 function
     * @param topicName
     * @return success OK , fail Error
     */
    @GetMapping("/createTopic")
    private TestEntity defaultTopic(@RequestParam("topicName") String topicName){
        TestEntity test = new TestEntity();
        try {
            kafkaAdmin.createOrModifyTopics(TopicBuilder.name(topicName)
                    .build());
            test.setTopicName(topicName);
            topicN = topicName;
            test.setContents("ok");
        } catch (Exception e) {
            System.out.println("Exception");
            test.setContents("Error");
        }
        return test;
    }

}
