package com.hyewon.springplayground.kafka;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@Slf4j
public class KafkaController {

    @Autowired
    private KafkaListenerService kafkaListenerService;

    @PostMapping("/stop")
    public void stopKafkaListener() {
        kafkaListenerService.stopListener();
        log.info("Kafka listener stopped at {}", System.currentTimeMillis());
    }

    @PostMapping("/start")
    public void startKafkaListener() {
        kafkaListenerService.startListener();
        log.info("Kafka listener stopped at {}", System.currentTimeMillis());
    }
}

