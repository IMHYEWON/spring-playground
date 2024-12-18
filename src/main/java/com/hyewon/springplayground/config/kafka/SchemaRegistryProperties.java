package com.hyewon.springplayground.config.kafka;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@RequiredArgsConstructor
@Component
@ConfigurationProperties(prefix = "spring.kafka.schema-registry")
public class SchemaRegistryProperties {

    private String url;
    private String username;
    private String password;

}
