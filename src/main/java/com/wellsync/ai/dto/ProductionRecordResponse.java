package com.wellsync.ai.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class ProductionRecordResponse {
    private UUID id;
    private UUID wellId;
    private Instant recordedAt;
    private Double oilRateBopd;
    private Double waterRateBwpd;
    private Double gasRateMscfd;
    private Double waterCutPercent;
    private Double steamOilRatio;
    private Double energyConsumptionKwh;
    private Double pumpEfficiencyPercent;
}
