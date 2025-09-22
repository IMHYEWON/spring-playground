package com.hyewon.springplayground.cache.dto;

import com.hyewon.springplayground.cache.domain.CachedData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalApiResponse {
    
    private String body;
    private int statusCode;
    private String contentType;
    private boolean fromCache;
    private String cacheKey;
    private String cachedAt;

    public static ExternalApiResponse fromLive(ResponseEntity<String> response) {
        return ExternalApiResponse.builder()
                .body(response.getBody())
                .statusCode(response.getStatusCode().value())
                .contentType(response.getHeaders().getContentType() != null ? 
                    response.getHeaders().getContentType().toString() : "application/json")
                .fromCache(false)
                .build();
    }

    public static ExternalApiResponse fromCachedData(CachedData cachedData) {
        return ExternalApiResponse.builder()
                .body(cachedData.getData())
                .statusCode(200)
                .contentType("application/json")
                .fromCache(true)
                .cacheKey(cachedData.getCacheKey())
                .cachedAt(cachedData.getCachedAt().toString())
                .build();
    }
}
