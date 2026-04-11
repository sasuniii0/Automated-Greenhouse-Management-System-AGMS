package com.example.Sensor.Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // ✅ WebClient.create() — no builder injection, works in MVC + WebFlux mixed mode
    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }
}