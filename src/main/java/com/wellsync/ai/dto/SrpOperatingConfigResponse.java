package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SrpOperatingConfigResponse {
    private UUID id;
    private UUID srpSystemId;
    private Double strokeLength;
    private Double spm;
    private Double pumpRpm;
    private Double vfdFrequency;
    private Double targetRpm;
    private Double pumpEfficiencyPercent;
    private Instant updatedAt;
}
