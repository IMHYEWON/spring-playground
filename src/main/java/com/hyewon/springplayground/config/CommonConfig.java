package com.hyewon.springplayground.config;

import com.hyewon.springplayground.config.property.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
public class CommonConfig {

}


