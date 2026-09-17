package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ReservoirResponse {
    private UUID id;
    private String name;
    private String formation;
    private String lithology;
    private Double apiGravity;
    private Double initialTemperatureC;
    private Double initialPressurePsi;
    private Double oilViscosityCp;
    private Double porosityPercent;
    private Double permeabilityMd;
    private Double depthM;
    private Instant createdAt;
    private Instant updatedAt;
}
