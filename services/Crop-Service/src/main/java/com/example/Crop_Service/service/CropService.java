package com.example.Crop_Service.service;

import com.example.Crop_Service.model.Crop;
import com.example.Crop_Service.repository.CropRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CropService {
    @Autowired
    private CropRepository cropRepository;

    public Crop addCrop(Crop crop) {
        crop.setStatus(Crop.CropStatus.SEEDLING);
        return cropRepository.save(crop);
    }

    public List<Crop> getAllCrops(Crop.CropStatus status) {
        if (status != null) {
            return cropRepository.findByStatus(status);
        }
        return cropRepository.findAll();
    }

    public Crop updateStatus(Long id, Crop.CropStatus newStatus) {
        Crop crop = cropRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crop not found"));

        validateTransition(crop.getStatus(), newStatus);

        crop.setStatus(newStatus);
        return cropRepository.save(crop);
    }

    private void validateTransition(Crop.CropStatus current, Crop.CropStatus next) {
        if (current == Crop.CropStatus.SEEDLING && next == Crop.CropStatus.VEGETATIVE) return;
        if (current == Crop.CropStatus.VEGETATIVE && next == Crop.CropStatus.HARVESTED) return;

        throw new IllegalStateException("Invalid state transition from " + current + " to " + next);
    }
}
