package com.hyewon.springplayground.redis.facade;

import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(Long productId, Long quantity) {
        RLock reddisonLock = redissonClient.getLock("stock:" + productId);
        try {
            // 10초 동안 락을 획득하려고 시도
            boolean available = reddisonLock.tryLock(10, 1, TimeUnit.SECONDS);

            if (!available) {
                System.out.println("락을 획득할 수 없습니다.");
                return;
            }
            stockService.decrease(productId, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // 모든 로직 정상적으로 수행 후 락 해제
            reddisonLock.unlock();
        }
    }
}
