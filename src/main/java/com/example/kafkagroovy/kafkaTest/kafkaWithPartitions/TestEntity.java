package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestEntity {

    //메세지의 내용들
    private String title;
    private String contents;
    private String topicName;
    private String dateTime;

}
