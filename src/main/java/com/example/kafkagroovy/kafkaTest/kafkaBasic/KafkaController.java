package com.example.kafkagroovy.kafkaTest.kafkaBasic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    private final KafkaProducer producer;

    @Autowired
    KafkaController(KafkaProducer producer){
        this.producer  = producer;
    }

//    @PostMapping("/message")
//    public String sendMessage(@RequestParam("message") String message){
//        this.producer.sendMessage(message);
//
//        return "success";
//    }
}
