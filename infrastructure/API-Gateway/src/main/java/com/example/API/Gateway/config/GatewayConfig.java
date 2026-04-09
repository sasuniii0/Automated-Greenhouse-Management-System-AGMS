package com.example.API.Gateway.config;

import com.example.API.Gateway.filter.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class GatewayConfig {

    private final JwtAuthFilter filter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("zone-service", r -> r.path("/api/zones/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://zone-service"))
                .route("sensor-service", r -> r.path("/api/sensors/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://sensor-service"))
                .route("automation-service", r -> r.path("/api/automation/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://automation-service"))
                .route("crop-service", r -> r.path("/api/crops/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://crop-service"))
                .build();
    }
}
