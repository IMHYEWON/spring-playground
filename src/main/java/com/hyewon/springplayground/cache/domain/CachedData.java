package com.hyewon.springplayground.cache.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("cached_data")
public class CachedData {
    
    @Id
    private String cacheKey;
    
    private String data;
    private LocalDateTime cachedAt;
    private LocalDateTime expiresAt;
    
    @TimeToLive
    private Long ttl; // TTL in seconds
    
    public static CachedData create(String cacheKey, String data, Long ttlSeconds) {
        LocalDateTime now = LocalDateTime.now();
        return CachedData.builder()
                .cacheKey(cacheKey)
                .data(data)
                .cachedAt(now)
                .expiresAt(now.plusSeconds(ttlSeconds))
                .ttl(ttlSeconds)
                .build();
    }
    
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
