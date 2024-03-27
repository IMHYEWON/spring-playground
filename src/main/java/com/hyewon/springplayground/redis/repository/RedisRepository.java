package com.hyewon.springplayground.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Repository
public class RedisRepository {
    private final RedisTemplate<String, String> redisTemplate;

    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // KEY에 대한 시퀀스 값을 1 증가시킨 후 반환
    public Long getNextSequence(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    // KEY에 대해 값이 없으면 0을 반환하고 있으면 시퀀스 값을 반환
    public Long getCurrentSequence(String key) {
        return redisTemplate
                .opsForValue()
                .get(key) == null ? 0 : Long.parseLong(redisTemplate.opsForValue().get(key));
    }

    // 재고를 락 (3초) (Key: 재고 ID, Value: lock)
    public Boolean lock(Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
    }

    // 재고 락 해제 (Key: 재고 ID)
    public Boolean unlock(Long key) {
        return redisTemplate.delete(generateKey(key));
    }

    // 재고 ID를 레디스용 스트링 키로 생성
    private String generateKey(Long key) {
        return key.toString();
    }
}
