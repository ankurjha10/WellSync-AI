package com.wellsync.ai.digitaltwin;

import com.wellsync.ai.entity.enums.RiskLevel;
import com.wellsync.ai.entity.enums.SystemStatus;
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
public class WellDigitalTwinState {
    private UUID wellId;
    private Instant lastUpdatedAt;

    private Double temperatureC;
    private Double pressurePsi;
    private Double viscosityCp;
    
    private Double pumpRpm;
    private Double rodLoadLbs;
    private Double spm;
    private Double vfdFrequencyHz;
    private Double strokeLengthIn;
    private Double pumpEfficiencyPercent;

    private Double steamPressurePsi;
    private Double steamTemperatureC;
    
    private Double productionRateBopd;
    private Double coolingRateCPerHour;

    private Integer currentRiskScore;
    private RiskLevel riskLevel;
    private SystemStatus systemStatus;
}

