package com.hyewon.springplayground.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@Slf4j
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaListenerService kafkaListenerService;

    private final AvroMessageSender avroMessageSender;

    @GetMapping("/stop")
    public void stopKafkaListener() {
        kafkaListenerService.stopListener();
        log.info("Kafka listener stopped at {}", System.currentTimeMillis());
    }

    @GetMapping("/start")
    public void startKafkaListener() {
        kafkaListenerService.startListener();
        log.info("Kafka listener stopped at {}", System.currentTimeMillis());
    }

    @GetMapping("/start/{offsetResetPolicy}")
    public void startKafkaListener(String offsetResetPolicy) {
        kafkaListenerService.startListener(offsetResetPolicy);
        log.info("Kafka listener stopped at {}", System.currentTimeMillis());
    }


    @GetMapping("/sendAvro")
    public void sendAvroMessage() {
        avroMessageSender.send("test-avro-topic");
        log.info("Avro message sent at {}", System.currentTimeMillis());
    }
}

