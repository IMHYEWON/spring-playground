package com.hyewon.springplayground.cache.controller;

import com.hyewon.springplayground.cache.dto.ExternalApiResponse;
import com.hyewon.springplayground.cache.service.ExternalApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/external")
@RequiredArgsConstructor
public class ExternalApiController {
    
    private final ExternalApiService externalApiService;

    @GetMapping("/realtime")
    public ResponseEntity<String> proxyRealtime(
            @RequestParam String param1,
            @RequestParam String param2) {

        try {
            ExternalApiResponse response = externalApiService.callExternalApiRealtime(param1, param2);
            
            Map<String, String> responseHeaders = new HashMap<>();
            responseHeaders.put("X-Response-Source", response.isFromCache() ? "cache" : "live");
            responseHeaders.put("X-Request-Type", "realtime");
            if (response.getCachedAt() != null) {
                responseHeaders.put("X-Cached-At", response.getCachedAt());
            }
            
            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(httpHeaders -> responseHeaders.forEach(httpHeaders::set))
                    .body(response.getBody());
                    
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"실시간 외부 API 호출 실패: " + e.getMessage() + "\"}");
        }
    }
    
    /**
     * 외부 API 프록시 - GET 요청 (캐시 우선)
     */
    @GetMapping("/cached")
    public ResponseEntity<String> proxyCached(
            @RequestParam String param1,
            @RequestParam String param2) {

        try {
            ExternalApiResponse response = externalApiService.callExternalApiCached(param1, param2);
            
            Map<String, String> responseHeaders = new HashMap<>();
            responseHeaders.put("X-Response-Source", response.isFromCache() ? "cache" : "live");
            responseHeaders.put("X-Request-Type", "cached");
            if (response.getCachedAt() != null) {
                responseHeaders.put("X-Cached-At", response.getCachedAt());
            }
            
            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(httpHeaders -> responseHeaders.forEach(httpHeaders::set))
                    .body(response.getBody());
                    
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"캐시 우선 외부 API 호출 실패: " + e.getMessage() + "\"}");
        }
    }
}