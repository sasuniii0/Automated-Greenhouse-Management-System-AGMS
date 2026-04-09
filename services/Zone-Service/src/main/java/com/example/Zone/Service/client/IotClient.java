package com.example.Zone.Service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "iot-client", url = "${iot.api.base-url}", configuration = FeignClient.class)
public interface IotClient {
    @PostMapping("/devices")
    Map<String, Object> registerDevice(@RequestBody Map<String, Object> deviceRequest);
}
