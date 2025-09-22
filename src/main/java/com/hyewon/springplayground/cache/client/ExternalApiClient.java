package com.hyewon.springplayground.cache.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(
    name = "externalApiClient",
    url = "${external.api.base-url:}",
    configuration = ExternalApiClientConfig.class
)
public interface ExternalApiClient {
    
    @GetMapping
    ResponseEntity<String> get(
            @RequestParam Map<String, String> queryParams,
            @RequestHeader Map<String, String> headers
    );
}
