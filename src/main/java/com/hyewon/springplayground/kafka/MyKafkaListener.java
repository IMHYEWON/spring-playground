package com.hyewon.springplayground.kafka;


import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
@Slf4j
@RequiredArgsConstructor
public class MyKafkaListener {

    private final StockService stockService;

    @KafkaListener(id = "myListener", topics = "hyewon-topic")
    public void listen(String message) throws InterruptedException {
        Long id = Long.parseLong(message);

        sleep(1000);
        stockService.createStock(id, 111L);

        log.info("String message sent at {}", System.currentTimeMillis());
        System.out.println("Received message: " + message);
    }
}