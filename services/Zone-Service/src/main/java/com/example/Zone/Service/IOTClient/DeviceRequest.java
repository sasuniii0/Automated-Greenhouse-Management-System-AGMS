package com.example.Zone.Service.IOTClient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DeviceRequest {
    private String zoneId;
    private String name;
    private String type;
    // getters + setters
}
