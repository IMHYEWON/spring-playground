package com.hyewon.springplayground.cache.client;

import feign.Logger;
import feign.Request;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class ExternalApiClientConfig {
    
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }
    
    @Bean
    public Request.Options requestOptions() {
        return new Request.Options(
            10, TimeUnit.SECONDS,  // connectTimeout
            30, TimeUnit.SECONDS,  // readTimeout
            true                   // followRedirects
        );
    }
    
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder() {
            @Override
            public Exception decode(String methodKey, feign.Response response) {
                log.error("Feign Client 오류 발생 - Method: {}, Status: {}, Reason: {}", 
                    methodKey, response.status(), response.reason());
                
                return new RuntimeException(
                    String.format("외부 API 호출 실패 - Status: %d, Reason: %s", 
                        response.status(), response.reason())
                );
            }
        };
    }
}
