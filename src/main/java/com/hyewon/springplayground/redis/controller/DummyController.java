package com.hyewon.springplayground.redis.controller;

import com.hyewon.springplayground.redis.repository.RedisRepository;
import com.hyewon.springplayground.redis.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/sequence")
@RestController
@Slf4j
public class DummyController {

    private final RedisRepository repository;
    private final StockService service;
    private static int count = 0;

    @GetMapping("/{sequenceName}")
    public Long getNextSequence(@PathVariable String sequenceName) {
        return repository.getNextSequence(sequenceName.toUpperCase());
    }

    @GetMapping("/{sequenceName}/current")
    public Long getCurrentSequence(@PathVariable String sequenceName) {
        return repository.getCurrentSequence(sequenceName.toUpperCase());
    }

    @GetMapping("/log")
    public String getLog() {

        log.trace("TRACE!!");
        log.debug("DEBUG!!");
        log.info("INFO!!");
        log.warn("WARN!!");
        log.error("ERROR!!");

        return "count: " + count++;
    }

    @GetMapping("/exception")
    public void getException() {
        service.createException();
    }
}