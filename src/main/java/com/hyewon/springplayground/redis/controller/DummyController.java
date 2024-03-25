package com.hyewon.springplayground.redis.controller;

import com.hyewon.springplayground.redis.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/sequence")
@RestController
public class DummyController {

    private final RedisRepository repository;
    private static int count = 0;

    @GetMapping("/{sequenceName}")
    public Long getNextSequence(@PathVariable String sequenceName) {
        return repository.getNextSequence(sequenceName.toUpperCase());
    }

    @GetMapping("/{sequenceName}/current")
    public Long getCurrentSequence(@PathVariable String sequenceName) {
        return repository.getCurrentSequence(sequenceName.toUpperCase());
    }
}