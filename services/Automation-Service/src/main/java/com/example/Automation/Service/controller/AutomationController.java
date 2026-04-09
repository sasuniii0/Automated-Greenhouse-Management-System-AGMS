package com.example.Automation.Service.controller;

import com.example.Automation.Service.model.AutomationLog;
import com.example.Automation.Service.service.AutomationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/automation")
@RequiredArgsConstructor
public class AutomationController {

    private final AutomationService automationService;

    @PostMapping("/process")
    public void process(@RequestBody Map<String, Object> telemetry) {
        automationService.processTelemetry(telemetry);
    }

    @GetMapping("/logs")
    public List<AutomationLog> getLogs() {
        return automationService.getAllLogs();
    }
}
