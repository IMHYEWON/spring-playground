package com.hyewon.springplayground.cache.service;

import com.hyewon.springplayground.cache.client.ExternalApiClient;
import com.hyewon.springplayground.cache.domain.CachedData;
import com.hyewon.springplayground.cache.dto.ExternalApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExternalApiService {
    
    private final ApiCacheService apiCacheService;
    private final ExternalApiClient externalApiClient;
    
    private static final long DEFAULT_TTL_SECONDS = 3600; // 1시간
    
    /**
     * 외부 API 호출 (실시간 우선 - 외부 API 먼저 호출, 실패 시 캐시)
     */
    public ExternalApiResponse callExternalApiRealtime(String param1, String param2) {
        try {
            ResponseEntity<String> response = externalApiClient.get(new HashMap<>(), createFixedHeaders());
            apiCacheService.cacheApiData(createCacheKey(param1, param2), response.getBody());
            
            return ExternalApiResponse.fromLive(response);
            
        } catch (Exception e) {
            Optional<CachedData> fallbackResponse = apiCacheService.getCachedApiData(createCacheKey(param1, param2));
            
            if (fallbackResponse.isPresent()) {
                return ExternalApiResponse.fromCachedData(fallbackResponse.get());
            }
            
            throw new RuntimeException("실시간 외부 API 호출 실패 및 캐시된 데이터 없음: " + e.getMessage(), e);
        }
    }
    
    /**
     * 외부 API 호출 (캐시 먼저, 실패 시 캐시된 데이터)
     */
    public ExternalApiResponse callExternalApiCached(String param1, String param2) {
        try {
            Optional<CachedData> cachedResponse = apiCacheService.getCachedApiData(createCacheKey(param1, param2));
            
            if (cachedResponse.isPresent()) {
                return ExternalApiResponse.fromCachedData(cachedResponse.get());
            }
            
            ResponseEntity<String> response = externalApiClient.get(new HashMap<>(), createFixedHeaders());
            
            apiCacheService.cacheApiData(createCacheKey(param1, param2), response.getBody());
            return ExternalApiResponse.fromLive(response);
            
        } catch (Exception e) {
            Optional<CachedData> fallbackResponse = apiCacheService.getCachedApiData(createCacheKey(param1, param2));
            
            if (fallbackResponse.isPresent()) {
                return ExternalApiResponse.fromCachedData(fallbackResponse.get());
            }
            
            throw new RuntimeException("Feign Client 호출 실패 및 캐시된 데이터 없음: " + e.getMessage(), e);
        }
    }

    public Map<String, String> createFixedHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        return headers;
    }
    
    /**
     * 캐시 키 생성 (param1:param2)
     */
    public String createCacheKey(String param1, String param2) {
        return param1 + ":" + param2;
    }
}