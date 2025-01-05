package com.hyewon.springplayground.kafka;

import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@Slf4j
@RequiredArgsConstructor
public class KafkaController {

    private final StockService stockService;

    private final KafkaListenerService kafkaListenerService;

    private final AvroMessageSender avroMessageSender;

    private final KafkaTemplate<String, String> kafkaTemplate;

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

    @GetMapping("/send/{message}")
    public void sendJsonMessage(@PathVariable String message) {
        kafkaTemplate.send("hyewon-topic", message);
        log.info("String message sent at {}", System.currentTimeMillis());
    }
}

