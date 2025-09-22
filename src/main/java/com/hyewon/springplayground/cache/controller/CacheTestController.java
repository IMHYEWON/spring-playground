package com.hyewon.springplayground.cache.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CacheTestController {
    
    @GetMapping("/cache-test")
    public String cacheTestPage() {
        return "cache-test";
    }
}
