package com.hyewon.springplayground.cache.service;

import com.hyewon.springplayground.cache.domain.CachedData;
import com.hyewon.springplayground.cache.repository.CachedDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiCacheService {
    
    private final CachedDataRepository cachedDataRepository;
    
    private static final long DEFAULT_TTL_SECONDS = 3600; // 1시간
    
    /**
     * 외부 API 데이터를 캐시에 저장
     */
    public void cacheApiData(String cacheKey, String data) {
        cacheApiData(cacheKey, data, DEFAULT_TTL_SECONDS);
    }
    
    /**
     * 외부 API 데이터를 캐시에 저장 (TTL 지정)
     */
    public void cacheApiData(String cacheKey, String data, Long ttlSeconds) {
        try {
            CachedData cachedData = CachedData.create(cacheKey, data, ttlSeconds);
            cachedDataRepository.save(cachedData);
            log.info("외부 API 데이터가 캐시에 저장되었습니다. Key: {}", cacheKey);
            
        } catch (Exception e) {
            log.error("외부 API 데이터 캐시 저장 중 오류 발생: {}", e.getMessage(), e);
        }
    }
    
    /**
     * 캐시된 외부 API 데이터 조회
     */
    public Optional<CachedData> getCachedApiData(String cacheKey) {
        try {
            Optional<CachedData> cachedData = cachedDataRepository.findById(cacheKey);
            
            if (cachedData.isPresent()) {
                CachedData data = cachedData.get();
                if (data.isExpired()) {
                    cachedDataRepository.deleteById(cacheKey);
                    return Optional.empty();
                }
                return cachedData;
            }
            
            return Optional.empty();
            
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}