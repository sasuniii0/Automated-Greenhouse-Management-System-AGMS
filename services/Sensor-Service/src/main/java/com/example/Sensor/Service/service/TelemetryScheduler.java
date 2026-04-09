package com.example.Sensor.Service.service;

import com.example.Sensor.Service.client.AutomationClient;
import com.example.Sensor.Service.client.ZoneClient;
import com.example.Sensor.Service.manager.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TelemetryScheduler {
    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private AutomationClient automationClient;

    private final WebClient webClient;
    private final Map<Long, Map<String, Object>> latestReadings = new ConcurrentHashMap<>();

    @Value("${iot.api.base-url}")
    private String iotBaseUrl;

    @Scheduled(fixedDelay = 10000)
    public void fetchAndPush() {
        System.out.println("Fetching telemetry...");
        try {
            List<Map<String, Object>> zones = zoneClient.getAllZones();
            for (Map<String, Object> zone : zones) {
                String deviceId = (String) zone.get("deviceId");
                Long zoneId = ((Number) zone.get("id")).longValue();

                if (deviceId != null) {
                    fetchWithRetry(deviceId, zoneId, true);
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching zones: " + e.getMessage());
        }
    }

    private void fetchWithRetry(String deviceId, Long zoneId, boolean canRetry) {
        try {
            Map telemetry = webClient.get()
                    .uri(iotBaseUrl + "/devices/telemetry/" + deviceId)
                    .header("Authorization", "Bearer " + tokenManager.getAccessToken())
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (telemetry != null) {
                telemetry.put("zoneId", zoneId);
                telemetry.put("deviceId", deviceId);

                latestReadings.put(zoneId, telemetry);

                automationClient.process(telemetry);
                System.out.println("Pushed telemetry for device: " + deviceId);
            }
        } catch (WebClientResponseException.Unauthorized e) {
            if (canRetry) {
                System.out.println("Token expired. Refreshing and retrying...");
                tokenManager.refresh();
                fetchWithRetry(deviceId, zoneId, false);
            } else {
                tokenManager.login();
            }
        } catch (Exception e) {
            System.err.println("Error fetching telemetry for device " + deviceId + ": " + e.getMessage());
        }
    }

    public Map<Long, Map<String, Object>> getLatestReadings() {
        return latestReadings;
    }
}
