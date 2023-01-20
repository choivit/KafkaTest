package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

// KafkaListener를 검사하는 설정. 이걸 지정해서 @KafkaListener가 활성화 됨.
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    // kafkaConsumer를 생성하기 위한 설정을 가지며 생성하는 역할을 함.
    private ConsumerFactory<String, Object> consumerFactory(String groupId){
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
//        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.TestEntity");
//        StringDeserializer stringDeserializer = new StringDeserializer();
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>();

        // 개별적으로 추가한 부분
        // 카프카 메세지는 역직렬화 처리할 경우 헤더의 값에 패키지명이 포함되는데 송/수신 측의 패키지 명이 서로 달라서 발생한 문제.
        // 이를 해결하기 위해 해당 옵션에 false를 넣어 헤더를 검사하지 않도록 조정.
//        jsonDeserializer.setUseTypeHeaders(false);

        // Deserializer에 대해서 신뢰하는 패키지를 지정한다. "*"를 지정하면 모두 신뢰하게 된다.
        jsonDeserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), jsonDeserializer);
//        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new ErrorHandlingDeserializer<>(new JsonDeserializer<>()));
    }

    // Kafka cluster로부터 메시지를 읽을 수 있도록 지정.
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> defaultKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        // consumerFactory를 전달하여 컨슈머를 생성.
        factory.setConsumerFactory(consumerFactory("defaultGroup"));
        // 동시에 읽은 컨슈머의 개수를 지정.
        factory.setConcurrency(1);
        // 메시지 리스너가 자동으로 실행할지 지정(true일 경우 서버가 부트업 되면서 자동 실행)
        factory.setAutoStartup(true);
        return factory;
    }
}
