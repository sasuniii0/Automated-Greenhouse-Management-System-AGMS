package com.example.Zone.Service.service;

import com.example.Zone.Service.client.IotClient;
import com.example.Zone.Service.dto.ZoneRequest;
import com.example.Zone.Service.dto.ZoneResponse;
import com.example.Zone.Service.model.Zone;
import com.example.Zone.Service.repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ZoneService {
    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private IotClient iotClient;

    public ZoneResponse createZone(ZoneRequest request) {
        if (request.getMinTemp() >= request.getMaxTemp()) {
            throw new IllegalArgumentException("Min temperature must be less than max temperature.");
        }

        // Register device with External IoT API
        String deviceName = "ZoneDevice-" + request.getName();
        String deviceId;
        try {
            Map<String, Object> iotResponse = iotClient.registerDevice(Map.of("name", deviceName, "zoneId", request.getName()));
            deviceId = iotResponse.get("deviceId").toString();
        } catch (Exception e) {
            System.err.println("IoT registration failed, using fallback UUID: " + e.getMessage());
            deviceId = UUID.randomUUID().toString();
        }

        Zone zone = new Zone();
        zone.setName(request.getName());
        zone.setMinTemp(request.getMinTemp());
        zone.setMaxTemp(request.getMaxTemp());
        zone.setDeviceId(deviceId);

        Zone savedZone = zoneRepository.save(zone);
        return mapToResponse(savedZone);
    }

    public List<ZoneResponse> getAllZones() {
        return zoneRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ZoneResponse getZoneById(Long id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));
        return mapToResponse(zone);
    }

    public ZoneResponse updateZone(Long id, ZoneRequest request) {
        if (request.getMinTemp() >= request.getMaxTemp()) {
            throw new IllegalArgumentException("Min temperature must be less than max temperature.");
        }

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Zone not found with id: " + id));

        zone.setName(request.getName());
        zone.setMinTemp(request.getMinTemp());
        zone.setMaxTemp(request.getMaxTemp());

        Zone updatedZone = zoneRepository.save(zone);
        return mapToResponse(updatedZone);
    }

    public void deleteZone(Long id) {
        zoneRepository.deleteById(id);
    }

    private ZoneResponse mapToResponse(Zone zone) {
        return ZoneResponse.builder()
                .id(zone.getId())
                .name(zone.getName())
                .minTemp(zone.getMinTemp())
                .maxTemp(zone.getMaxTemp())
                .deviceId(zone.getDeviceId())
                .createdAt(zone.getCreatedAt())
                .build();
    }
}
