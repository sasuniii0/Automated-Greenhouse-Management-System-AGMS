package com.example.Crop_Service.repository;

import com.example.Crop_Service.model.Crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findByStatus(Crop.CropStatus status);

}
