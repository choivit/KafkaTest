package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.controller;

import com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.TestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProducerController {

    // Producer를 위한 KafkaTemplate을 지정.
    // 메세지는 POST로 받아서 kafka로 전달.
    private final KafkaTemplate<String, Object> kafkaProducerTemplate;

//    @Value("${kafka.topic-with-key}")
    public String TOPIC_WITH_KEY;

    public ProducerController(KafkaTemplate<String, Object> kafkaProducerTemplate) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
    }

    @PostMapping("produce")
    public ResponseEntity<?> produceMessage(@RequestBody TestEntity testEntity){
        System.out.println("produceMessage start !");
        log.info("testEntity={}", testEntity);

        // kafkaProducerTemplate.send를 통해 메세지 전송. 이때 토픽을 지정하고 메세지를 전송.
        // ListenableFuture를 통하여 전송 결과 확인 가능.
        ListenableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(testEntity.getTopicName(), testEntity);
        System.out.println("produceMessage good !");

        // 메세지 처리는 비동기로 처리하기 때문에 callback 지정.
        // Future 혹은 Callback 선택 가능.
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("Fail to send message to broker: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("Send message with offset: {}, partition: {}", result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }
        });

        return ResponseEntity.ok(testEntity);
    }

    @PostMapping("produce-with-key/{key}")
    public ResponseEntity<?> produceMessageWithKey(@PathVariable("key") String key, @RequestBody TestEntity testEntity){

        log.info("key = {}, testEntity = {}", key, testEntity);
        System.out.println("변환되는 건가 ? : " + key);
//        String strKey = String.valueOf(key);

//        System.out.println("전달되게 변환은 된건가? : " + strKey);
        // 키를 함께 전달하여 키에 의한 파티셔닝을 수행하도록 전달.
        ListenableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(testEntity.getTopicName(), key, testEntity);
        System.out.println("이미 지난건가? : " + key);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("메세지를 보낼 수 없습니다: {}", ex.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                log.info("키와 함께 보낸 메세지: {}, offset: {}, partition: {}", key, result.getRecordMetadata().offset(), result.getRecordMetadata().partition());
            }
        });

        return ResponseEntity.ok(testEntity);
    }
}
