package com.example.Zone.Service.controller;

import com.example.Zone.Service.dto.ZoneRequest;
import com.example.Zone.Service.dto.ZoneResponse;
import com.example.Zone.Service.service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {
    private ZoneService zoneService;

    @PostMapping
    public ResponseEntity<ZoneResponse> createZone(
            @Valid @RequestBody ZoneRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(zoneService.createZone(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ZoneResponse> getZone(
            @PathVariable Long id) {
        return ResponseEntity.ok(zoneService.getZone(id));
    }

    @GetMapping
    public ResponseEntity<List<ZoneResponse>> getAllZones() {
        return ResponseEntity.ok(zoneService.getAllZones());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZoneResponse> updateZone(
            @PathVariable Long id,
            @Valid @RequestBody ZoneRequest request) {
        return ResponseEntity.ok(zoneService.updateZone(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.noContent().build();
    }
}
