package com.hyewon.springplayground.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

    @KafkaListener(id = "myListener", topics = "hyewon-topic")
    public void listen(String message) {
        System.out.println("Received message: " + message);
    }
}