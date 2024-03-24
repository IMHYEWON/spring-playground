package com.hyewon.springplayground.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
public class Controller {

    @Retryable
    @GetMapping("/retry")
    public String outer() {
        return retryMethod();
    }

    @Retryable(retryFor = IllegalArgumentException.class, maxAttempts = 2)
    @GetMapping("/retry2")
    public String outer2() {
        return retryMethod2();
    }

    private String retryMethod() {
        System.out.println("retryMethod");

        throw new RuntimeException("retryMethod");
    }

    private String retryMethod2() {
        System.out.println("retryMethod2");

        throw new IllegalArgumentException("retryMethod2");
    }

    @Recover
    private String fallBack(Exception e) {
        log.warn("fallBack  {}", e.getMessage());
        return null;
    }
}
