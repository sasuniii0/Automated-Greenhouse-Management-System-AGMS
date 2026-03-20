package com.example.Zone.Service.service;

import com.example.Zone.Service.IOTClient.DeviceRequest;
import com.example.Zone.Service.IOTClient.DeviceResponse;
import com.example.Zone.Service.IOTClient.IotClient;
import com.example.Zone.Service.dto.ZoneRequest;
import com.example.Zone.Service.dto.ZoneResponse;
import com.example.Zone.Service.entity.Zone;
import com.example.Zone.Service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService {
     private ZoneRepository zoneRepository;
     private IotClient iotClient;

    // Store IoT token (or fetch via TokenManager)
    @Value("${iot.api.token:}")
    private String iotToken;

    public ZoneResponse createZone(ZoneRequest request) {
        // Business validation
        if (request.getMinTemp() >= request.getMaxTemp()) {
            throw new IllegalArgumentException(
                    "minTemp must be less than maxTemp");
        }

        // Register device with IoT API
        DeviceRequest deviceReq = new DeviceRequest();
        deviceReq.setZoneId(String.valueOf(System.currentTimeMillis()));
        deviceReq.setName(request.getName());
        deviceReq.setType("GREENHOUSE_SENSOR");

        DeviceResponse deviceResp = iotClient.registerDevice(
                deviceReq, "Bearer " + iotToken);

        // Save zone with returned deviceId
        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setMinTemp(request.getMinTemp());
        zone.setMaxTemp(request.getMaxTemp());
        zone.setDeviceId(deviceResp.getDeviceId());

        Zone saved = zoneRepository.save(zone);
        return toResponse(saved);
    }

    public ZoneResponse getZone(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Zone not found: " + id));
        return toResponse(zone);
    }

    public List<ZoneResponse> getAllZones() {
        return zoneRepository.findAll()
                .stream().map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ZoneResponse updateZone(Long id, ZoneRequest req) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found"));
        if (req.getMinTemp() >= req.getMaxTemp())
            throw new IllegalArgumentException(
                    "minTemp must be less than maxTemp");
        zone.setName(req.getName());
        zone.setMinTemp(req.getMinTemp());
        zone.setMaxTemp(req.getMaxTemp());
        return toResponse(zoneRepository.save(zone));
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    private ZoneResponse toResponse(Zone z) {
        return new ZoneResponse(z.getId(), z.getName(),
                z.getMinTemp(), z.getMaxTemp(),
                z.getDeviceId(), z.getCreatedAt());
    }
}
