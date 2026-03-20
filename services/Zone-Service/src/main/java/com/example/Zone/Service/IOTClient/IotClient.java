package com.example.Zone.Service.IOTClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "iot-api",
        url = "http://104.211.95.241:8080")
public interface IotClient {
    @PostMapping("/api/devices")
    DeviceResponse registerDevice(
            @RequestBody DeviceRequest request,
            @RequestHeader("Authorization") String token);

    @DeleteMapping("/api/devices/{deviceId}")
    void deleteDevice(
            @PathVariable String deviceId,
            @RequestHeader("Authorization") String token);
}
