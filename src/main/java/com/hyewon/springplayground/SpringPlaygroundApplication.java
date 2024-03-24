package com.hyewon.springplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class SpringPlaygroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPlaygroundApplication.class, args);
    }

}
