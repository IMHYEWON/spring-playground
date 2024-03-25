package com.hyewon.springplayground.redis.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    private final RedisTemplate<String, Long> redisTemplate;

    public RedisRepository(RedisTemplate<String, Long> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long getNextSequence(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public Long getCurrentSequence(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
