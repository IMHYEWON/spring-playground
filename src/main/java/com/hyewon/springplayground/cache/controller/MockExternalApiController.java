package com.hyewon.springplayground.cache.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/mock/external")
public class MockExternalApiController {
    
    private final Random random = new Random();
    private boolean isHealthy = true;
    
    /**
     * 정상 응답을 반환하는 Mock API
     */
    @GetMapping("/success")
    public ResponseEntity<Map<String, Object>> success() {
        log.info("Mock API - 정상 응답");
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "정상 응답입니다");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("data", Map.of(
            "userId", 12345,
            "name", "홍길동",
            "email", "hong@example.com"
        ));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 랜덤하게 실패하는 Mock API (50% 확률로 실패)
     */
    @GetMapping("/unreliable")
    public ResponseEntity<Map<String, Object>> unreliable() {
        log.info("Mock API - 불안정한 API 호출");
        
        if (random.nextBoolean()) {
            // 50% 확률로 실패
            log.warn("Mock API - 실패 응답");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "status", "error",
                        "message", "서버 내부 오류",
                        "timestamp", LocalDateTime.now().toString()
                    ));
        }
        
        // 50% 확률로 성공
        log.info("Mock API - 성공 응답");
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "불안정하지만 성공한 응답");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("data", Map.of(
            "randomValue", random.nextInt(1000),
            "serverTime", System.currentTimeMillis()
        ));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 서버 상태를 제어하는 API
     */
    @PostMapping("/health")
    public ResponseEntity<Map<String, Object>> setHealth(@RequestParam boolean healthy) {
        this.isHealthy = healthy;
        log.info("Mock API 서버 상태 변경: {}", healthy ? "정상" : "장애");
        
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "서버 상태가 " + (healthy ? "정상" : "장애") + "으로 설정되었습니다",
            "isHealthy", healthy
        ));
    }
    
    /**
     * 서버 상태에 따라 응답하는 API
     */
    @GetMapping("/health-dependent")
    public ResponseEntity<Map<String, Object>> healthDependent() {
        log.info("Mock API - 상태 의존적 API 호출 (현재 상태: {})", isHealthy ? "정상" : "장애");
        
        if (!isHealthy) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(
                        "status", "error",
                        "message", "서버가 일시적으로 사용할 수 없습니다",
                        "timestamp", LocalDateTime.now().toString()
                    ));
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "서버가 정상적으로 동작 중입니다");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("data", Map.of(
            "serverStatus", "healthy",
            "uptime", "2h 30m 15s"
        ));
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * 지연 응답을 반환하는 API
     */
    @GetMapping("/slow")
    public ResponseEntity<Map<String, Object>> slow() throws InterruptedException {
        log.info("Mock API - 지연 응답 (3초 대기)");
        
        Thread.sleep(3000); // 3초 대기
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "지연된 응답입니다");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("processingTime", "3000ms");
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * POST 요청을 받는 Mock API
     */
    @PostMapping("/data")
    public ResponseEntity<Map<String, Object>> postData(@RequestBody(required = false) Map<String, Object> requestBody) {
        log.info("Mock API - POST 요청 수신: {}", requestBody);
        
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "POST 요청이 성공적으로 처리되었습니다");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("receivedData", requestBody);
        response.put("processedAt", System.currentTimeMillis());
        
        return ResponseEntity.ok(response);
    }
}
