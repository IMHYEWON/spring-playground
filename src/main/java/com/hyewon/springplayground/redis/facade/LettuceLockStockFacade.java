package com.hyewon.springplayground.redis.facade;

import com.hyewon.springplayground.redis.repository.RedisRepository;
import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final StockService stockService;
    private final RedisRepository redisRepository;

    /**
     * Lettuce 라이브러리를 이용한 락 구현
     * - spin Lock 방식으로 구현이 쉬우나, 레디스에 부하를 줄 가능성 -> Thread.sleep()을 이용한 대기 시간 설정
     * - 레디스의 setnx, getset을 이용한 락 구현
     * **/
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (!redisRepository.lock(id)) {
            Thread.sleep(100);
        }

        try {
            stockService.decrease(id, quantity);
        } finally {
            redisRepository.unlock(id);
        }
    }
}
