package com.example.Zone.Service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "zone")
public class Zone {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Double minTemp;
    private Double maxTemp;
    private String deviceId;
    private LocalDateTime createdAt;
}
