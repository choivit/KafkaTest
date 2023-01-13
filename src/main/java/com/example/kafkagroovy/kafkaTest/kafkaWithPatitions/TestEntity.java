package com.example.kafkagroovy.kafkaTest.kafkaWithPatitions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    //메세지의 내용들
    private String title;
    private String contents;
    private LocalDateTime time;
    private String topicName;

}
