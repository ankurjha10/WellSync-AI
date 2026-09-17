package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReservoirRequest {
    @NotBlank(message = "Name is required")
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
}
