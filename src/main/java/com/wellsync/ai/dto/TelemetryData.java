package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryData {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Timestamp is required")
    private Instant timestamp;

    private Double temperatureC;
    private Double pressurePsi;
    private Double viscosityCp;
    private Double pumpRpm;
    private Double rodLoadLbs;
    private Double spm;
    private Double vfdFrequencyHz;
    private Double strokeLengthIn;
    private Double steamPressurePsi;
    private Double steamTemperatureC;
    private Double productionRateBopd;
    private Double pumpEfficiencyPercent;
}
