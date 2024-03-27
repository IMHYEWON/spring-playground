package com.hyewon.springplayground.redis.facade;

import com.hyewon.springplayground.redis.domain.Stock;
import com.hyewon.springplayground.redis.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LettuceLockStockFacadeTest {

    @Autowired
    private LettuceLockStockFacade lettuceLockStockFacade;

    @Autowired
    private StockRepository stockRepository;
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
    public void 동시에_100개의요청() throws InterruptedException {
        // 100개의 스레드 생성
        int threadCount = 100;

        // 32개의 스레드를 가진 스레드 풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // CountDownLatch : 스레드가 종료될 때까지 대기하는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            // 스레드 풀에 작업을 전달
            executorService.submit(() -> {
                try {
                    // 재고 감소
                    lettuceLockStockFacade.decrease(1L, 1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    // CountDownLatch를 감소시킴 (스레드 종료)
                    latch.countDown();
                }
            });
        }

        // 모든 스레드가 종료될 때까지 대기
        latch.await();

        // 재고 조회
        Stock stock = stockRepository.findById(1L).orElseThrow();

        // 100 - (100 * 1) = 0
        assertEquals(0, stock.getQuantity());
    }
}