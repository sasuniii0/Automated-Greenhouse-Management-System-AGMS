package com.example.Crop_Service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "crops")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long zoneId;

    @Enumerated(EnumType.STRING)
    private CropStatus status;

    private LocalDateTime plantedAt;
    private LocalDateTime updatedAt;

    public enum CropStatus {
        SEEDLING, VEGETATIVE, HARVESTED
    }

    @PrePersist
    protected void onCreate() {
        plantedAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
