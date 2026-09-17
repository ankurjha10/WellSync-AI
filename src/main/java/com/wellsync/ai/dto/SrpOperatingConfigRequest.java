package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SrpOperatingConfigRequest {
    @NotNull(message = "SRP System ID is required")
    private UUID srpSystemId;

    private Double strokeLength;
    private Double spm;
    private Double pumpRpm;
    private Double vfdFrequency;
    private Double targetRpm;
    private Double pumpEfficiencyPercent;
}
