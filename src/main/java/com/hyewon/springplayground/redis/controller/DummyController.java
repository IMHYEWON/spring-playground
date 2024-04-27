package com.hyewon.springplayground.redis.controller;

import com.hyewon.springplayground.redis.dto.DummyDto;
import com.hyewon.springplayground.redis.repository.RedisRepository;
import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/sequence")
@RestController
@Slf4j
public class DummyController {

    private final RedisRepository repository;
    private final StockService service;

    @GetMapping("/{sequenceName}")
    public Long getNextSequence(@PathVariable String sequenceName) {
        return repository.getNextSequence(sequenceName.toUpperCase());
    }

    @GetMapping("/{sequenceName}/current")
    public Long getCurrentSequence(@PathVariable String sequenceName) {
        return repository.getCurrentSequence(sequenceName.toUpperCase());
    }

    @GetMapping("/exception")
    public void getException() {
        service.createException();
    }

    @PostMapping("/exception")
    public void createException(@RequestBody DummyDto dummyDto) {
        service.createException(dummyDto);
    }
}