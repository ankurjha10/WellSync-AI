package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SteamInjectionResponse {
    private UUID id;
    private UUID cssCycleId;
    private Double steamRateKgHr;
    private Double steamVolumeKg;
    private Double steamTemperatureC;
    private Double injectionPressurePsi;
    private Integer injectionDurationMinutes;
    private Double steamQualityPercent;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
}
