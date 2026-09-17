package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SteamInjectionRequest {
    @NotNull(message = "CSS Cycle ID is required")
    private UUID cssCycleId;

    private Double steamRateKgHr;
    private Double steamVolumeKg;
    private Double steamTemperatureC;
    private Double injectionPressurePsi;
    private Integer injectionDurationMinutes;
    private Double steamQualityPercent;
    private Instant startTime;
    private Instant endTime;
}
