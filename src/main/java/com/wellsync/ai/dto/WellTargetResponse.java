package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class WellTargetResponse {
    private UUID id;
    private UUID wellId;
    private Double minRpm;
    private Double maxRpm;
    private Double targetRpm;
    private Double minTemperatureC;
    private Double maxViscosityCp;
    private Double maxRodLoadLbs;
    private Double targetOilRateBopd;
    private Double maxEnergyKwh;
    private Instant updatedAt;
}
