package com.example.kafkagroovy.kafkaTest.kafkaWithPatitions;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

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
            System.out.println("생성은 오죠?");
            kafkaAdmin.createOrModifyTopics(TopicBuilder.name(topicName)
                    .build());
            System.out.println("생성은 안오나욥??");
            return "OK";
        } catch (Exception e) {
            System.out.println("Exception");
            return "Error";
        }
    }

}
