package com.hyewon.springplayground.kafka;

import com.example.avro.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AvroMessageSender {

    private final KafkaTemplate<String, Object> avroKafkaTemplate;

    public void send(String topic) {
        avroKafkaTemplate.send(topic, createAvroSchema());
    }

    private User createAvroSchema() {
        return User.newBuilder()
                .setUserId("S001")
                .setUserName("hyewon")
                .setAge(30)
                .setEmail("h@gmail.com")
                .setIsActive(true)
                .build();
    }

}
