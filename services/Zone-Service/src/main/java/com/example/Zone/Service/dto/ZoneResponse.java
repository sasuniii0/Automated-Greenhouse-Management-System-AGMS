package com.example.Zone.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ZoneResponse {
    private Long id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
    private LocalDateTime createdAt;
}
