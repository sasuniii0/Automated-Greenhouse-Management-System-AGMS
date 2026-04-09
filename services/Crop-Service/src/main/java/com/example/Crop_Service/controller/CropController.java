package com.example.Crop_Service.controller;

import com.example.Crop_Service.model.Crop;
import com.example.Crop_Service.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crops")
public class CropController {
    @Autowired
    private CropService cropService;

    @PostMapping
    public Crop addCrop(@RequestBody Crop crop) {
        return cropService.addCrop(crop);
    }

    @GetMapping
    public List<Crop> getAllCrops(@RequestParam(required = false) Crop.CropStatus status) {
        return cropService.getAllCrops(status);
    }

    @PutMapping("/{id}/status")
    public Crop updateStatus(@PathVariable Long id, @RequestParam Crop.CropStatus status) {
        return cropService.updateStatus(id, status);
    }
}
