package com.example.Zone.Service.controller;

import com.example.Zone.Service.dto.ZoneRequest;
import com.example.Zone.Service.dto.ZoneResponse;
import com.example.Zone.Service.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    @Autowired
    private ZoneService zoneService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneResponse createZone(@RequestBody ZoneRequest request) {
        return zoneService.createZone(request);
    }

    @GetMapping
    public List<ZoneResponse> getAllZones() {
        return zoneService.getAllZones();
    }

    @GetMapping("/{id}")
    public ZoneResponse getZoneById(@PathVariable Long id) {
        return zoneService.getZoneById(id);
    }

    @PutMapping("/{id}")
    public ZoneResponse updateZone(@PathVariable Long id, @RequestBody ZoneRequest request) {
        return zoneService.updateZone(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
    }
}
