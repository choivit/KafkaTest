package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import javax.annotation.PostConstruct;

//@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {

    public final static String DEFAULT_TOPIC = "choi0130";

    // 키를 지정해주는 부분
//    @Value("${kafka.topic-with-key}")
//    public String TOPIC_WITH_KEY;

    private final KafkaAdmin kafkaAdmin;

    private NewTopic defaultTopic(){
        return TopicBuilder.name(DEFAULT_TOPIC)
                .partitions(2)
                .replicas(1)
                .build();
    }

    private  NewTopic topicWithKey(){
        return  TopicBuilder.name(DEFAULT_TOPIC)
                .partitions(2)
//                .replicas(1)
                .build();
    }

    // 빈이 생성되고 나면 마지막으로 수행되는 어노테이션.
    @PostConstruct
    public void init(){
        // 토픽이 있다면 수정하고 없다면 새로 생성.
        kafkaAdmin.createOrModifyTopics(defaultTopic());
        kafkaAdmin.createOrModifyTopics(topicWithKey());
    }
}
