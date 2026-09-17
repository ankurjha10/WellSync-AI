package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WellTargetRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    private Double minRpm;
    private Double maxRpm;
    private Double targetRpm;
    private Double minTemperatureC;
    private Double maxViscosityCp;
    private Double maxRodLoadLbs;
    private Double targetOilRateBopd;
    private Double maxEnergyKwh;
}
