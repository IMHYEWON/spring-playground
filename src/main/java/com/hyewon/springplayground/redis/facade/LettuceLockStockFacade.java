package com.hyewon.springplayground.redis.facade;

import com.hyewon.springplayground.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final RedisRepository redisRepository;

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (!redisRepository.lock(id)) {
            Thread.sleep(100);
        }

        try {
            // TODO : 재고감소 로직
            // stockService.decrease(id, quantity);
        } finally {
            redisRepository.unlock(id);
        }
    }
}
