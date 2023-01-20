package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class KafkaProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    //Producer는 ProducerFactory를 이용. 메세지를 전송하기 위해서는 KafkaProducerTemplate를 이용.
    private ProducerFactory<String, Object> producerFactory(){

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer); // kafka broker의 엔드포인트
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        log.info("header info = {}", configProps);

        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Object> KafkaProducerTemplate(){
        // KafkaTemplate는 Kafka와 오퍼레이션을 수행하기 위한 편의 기능을 가진 도구
        // 여기서는 producerFactory를 추가하여 kafka와 오퍼레이션을 수행할 수 있는 설정값을 전달하고 있다.
        return new KafkaTemplate<>(producerFactory());
    }
}
