package com.hyewon.springplayground.redis;

import com.hyewon.springplayground.redis.repository.RedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisRepository repository;

    @Test
    public void test() {
        String key = "test";
        Long nextSequence = repository.getNextSequence(key);
        Long nextSequence1 = repository.getNextSequence(key);
        System.out.println("nextSequence = " + nextSequence);
        System.out.println("nextSequence = " + nextSequence1);
    }
}
