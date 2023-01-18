package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaAdminConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private  String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        System.out.println("KafkaAdminConfig.kafkaAdmin >>>>>>");
        return new KafkaAdmin(configs);
    }
}
