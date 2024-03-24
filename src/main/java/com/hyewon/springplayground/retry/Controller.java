package com.hyewon.springplayground.retry;

import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
public class Controller {

    @Retryable
    @GetMapping("/retry")
    public String outer() {
        return retryMethod();
    }

    @Retryable(include = IllegalArgumentException.class)
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
}
