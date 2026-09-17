package com.wellsync.ai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ProductionRecordRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Recorded At timestamp is required")
    private Instant recordedAt;

    private Double oilRateBopd;
    private Double waterRateBwpd;
    private Double gasRateMscfd;
    private Double waterCutPercent;
    private Double steamOilRatio;
    private Double energyConsumptionKwh;
    private Double pumpEfficiencyPercent;
}
