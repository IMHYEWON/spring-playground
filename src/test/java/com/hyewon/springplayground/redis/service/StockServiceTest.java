package com.hyewon.springplayground.redis.service;

import com.hyewon.springplayground.redis.domain.Stock;
import com.hyewon.springplayground.redis.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockService stockService;

    @BeforeEach
    public void insert() {
        // 재고 100개 생성
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void delete() {
        stockRepository.deleteAll();
    }

    @Test
    public void 동시에_100개의요청() {
        for (int i = 0; i < 100; i++) {
            stockService.decrease(1L, 1L);
        }
    }
}