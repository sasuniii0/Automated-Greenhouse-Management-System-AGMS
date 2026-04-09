package com.example.Zone.Service.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Autowired
    private TokenManager tokenManager;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (requestTemplate.url().contains("/devices")) {
                requestTemplate.header("Authorization", "Bearer " + tokenManager.getAccessToken());
            }
        };
    }
}
