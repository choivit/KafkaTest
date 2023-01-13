package com.example.kafkagroovy.kafkaTest.kafkaWithPatitions;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import javax.annotation.PostConstruct;

//@Configuration
public class KafkaTopicConfig {

    public final static String DEFAULT_TOPIC = "choi3";

//    @Value("${kafka.topic-with-key}")
//    public String TOPIC_WITH_KEY;

    @Autowired
    private KafkaAdmin kafkaAdmin;

    private NewTopic defaultTopic(){
        return TopicBuilder.name(DEFAULT_TOPIC)
                .partitions(2)
                .replicas(2)
                .build();
    }

//    private  NewTopic topicWithKey(){
//        return  TopicBuilder.name(TOPIC_WITH_KEY)
//                .partitions(2)
//                .replicas(2)
//                .build();
//    }

    // 빈이 생성되고 나면 마지막으로 수행되는 어노테이션.
    @PostConstruct
    public void init(){
        kafkaAdmin.createOrModifyTopics(defaultTopic());
    }
}
