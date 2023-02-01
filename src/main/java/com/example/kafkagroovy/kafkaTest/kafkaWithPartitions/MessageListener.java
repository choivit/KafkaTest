package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions;

import com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.controller.KafkaTopicController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageListener {

    // topic, groupid, contatinerFactory 를 파라미터로 지정하기.
    // topic - 메세지를 송/숮신하기 위한 토픽 지정.
    // groupId -  컨슈머 그룹 아이디. 그룹에 속한 컨슈머는 할당된 파티션만 조회.
    // contatinerFactory - 이전에 지정한 컨테이너 팩토리 이름을 지정. 여기서는 KafkaConsumerConfig에서 지정한 걸로 지정.
//    @KafkaListener(topics = KafkaTopicConfig.DEFAULT_TOPIC, containerFactory = "defaultKafkaListenerContainerFactory")
//    public void listenDefaultTopic(Object record) {
//        // 메세지를 수신하면 토픽 이름과 레코드를 콘솔로 출력.
//        log.info("receive Message form {}, values {}", KafkaTopicConfig.DEFAULT_TOPIC, record);
//    }

    // 메세지를 수신하면 단순하게 로깅하는 코드.
    // topic 값은 있는 그대로를 읽도록 spel을 지원.
    // contatinerFactory는 기본 프로그램에서 생성한 컨테이너 팩토리 그대로 지정.

    @KafkaListener(topics = "choi0130", containerFactory = "defaultKafkaListenerContainerFactory")
    public void listenTopicWithKey(TestEntity testEntity) {
        // 메세지를 수신하면 토픽 이름과 레코드를 콘솔로 출력.

        log.info("receive Message form {}, values {} with key", testEntity);
    }
}
