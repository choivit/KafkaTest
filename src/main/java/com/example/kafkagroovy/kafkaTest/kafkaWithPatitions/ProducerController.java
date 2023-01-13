package com.example.kafkagroovy.kafkaTest.kafkaWithPatitions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api")
public class ProducerController {

    // Producer를 위한 KafkaTemplate을 지정.
    // 메세지는 POST로 받아서 kafka로 전달.
    private final KafkaTemplate<String, Object> kafkaProducerTemplate;

    public ProducerController(KafkaTemplate<String, Object> kafkaProducerTemplate) {
        this.kafkaProducerTemplate = kafkaProducerTemplate;
    }

    @PostMapping("/produce")
    public ResponseEntity<?> produceMessage(@RequestBody TestEntity testEntity){
        testEntity.setTime(LocalDateTime.now());

        // kafkaProducerTemplate.send를 통해 메세지 전송. 이때 토픽을 지정하고 메세지를 전송.
        // ListenableFuture를 통하여 전송 결과 확인 가능.
        ListenableFuture<SendResult<String, Object>> future = kafkaProducerTemplate.send(testEntity.getTopicName(), testEntity);

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
}
