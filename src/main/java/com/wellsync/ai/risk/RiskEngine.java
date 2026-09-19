package com.wellsync.ai.risk;

import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.AlertRequest;
import com.wellsync.ai.entity.enums.AlertSeverity;
import com.wellsync.ai.entity.enums.AlertType;
import com.wellsync.ai.entity.enums.RiskLevel;
import com.wellsync.ai.entity.enums.SystemStatus;
import com.wellsync.ai.dto.AlertResponse;
import com.wellsync.ai.service.AlertService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RiskEngine {

    private final AlertService alertService;
    private final SimpMessagingTemplate messagingTemplate;

    public List<String> evaluateRisk(WellDigitalTwinState state) {
        int riskScore = 0;
        List<String> factors = new ArrayList<>();

        if (state.getTemperatureC() != null && state.getTemperatureC() < 50.0) {
            riskScore += 25;
            factors.add("Low reservoir temperature (< 50°C)");
        }

        if (state.getViscosityCp() != null && state.getViscosityCp() > 1000.0) {
            riskScore += 25;
            factors.add("High oil viscosity (> 1000 cP)");
        }

        if (state.getRodLoadLbs() != null && state.getRodLoadLbs() > 15000.0) {
            riskScore += 25;
            factors.add("Elevated rod load (> 15000 lbs)");
        }

        if (state.getPumpRpm() != null && state.getPumpRpm() > 10.0 && 
            state.getViscosityCp() != null && state.getViscosityCp() > 800.0) {
            riskScore += 15;
            factors.add("Pump RPM too high for current viscosity");
        }

        if (state.getCoolingRateCPerHour() != null && state.getCoolingRateCPerHour() > 2.0) {
            riskScore += 10;
            factors.add("Rapid cooling trend detected");
        }

        riskScore = Math.min(100, Math.max(0, riskScore));
        
        RiskLevel previousRiskLevel = state.getRiskLevel();
        RiskLevel currentRiskLevel = determineRiskLevel(riskScore);

        state.setCurrentRiskScore(riskScore);
        state.setRiskLevel(currentRiskLevel);

        if (riskScore >= 80) {
            state.setSystemStatus(SystemStatus.CRITICAL);
        } else if (riskScore >= 60) {
            state.setSystemStatus(SystemStatus.HIGH_RISK);
        } else if (state.getTemperatureC() != null && state.getTemperatureC() < 55.0) {
            state.setSystemStatus(SystemStatus.COOLING);
        } else {
            state.setSystemStatus(SystemStatus.NORMAL);
        }

        if (riskScore >= 60 && currentRiskLevel != previousRiskLevel) {
            generateAlert(state, factors, currentRiskLevel);
        }

        return factors;
    }

    private RiskLevel determineRiskLevel(int score) {
        if (score >= 80) return RiskLevel.CRITICAL;
        if (score >= 60) return RiskLevel.HIGH;
        if (score >= 30) return RiskLevel.WARNING;
        return RiskLevel.HEALTHY;
    }

    private void generateAlert(WellDigitalTwinState state, List<String> factors, RiskLevel currentRiskLevel) {
        try {
            AlertRequest alert = new AlertRequest();
            alert.setWellId(state.getWellId());
            alert.setAlertType(AlertType.HIGH_ROD_LOAD);
            
            AlertSeverity severity = AlertSeverity.INFO;
            if (currentRiskLevel == RiskLevel.CRITICAL) severity = AlertSeverity.CRITICAL;
            else if (currentRiskLevel == RiskLevel.HIGH) severity = AlertSeverity.WARNING;
            else if (currentRiskLevel == RiskLevel.WARNING) severity = AlertSeverity.WARNING;
            
            alert.setSeverity(severity);
            alert.setTitle("High Risk Detected: " + currentRiskLevel.name());
            alert.setMessage("Risk score reached " + state.getCurrentRiskScore() + ". Factors: " + String.join(", ", factors));
            alert.setRiskScore((double) state.getCurrentRiskScore());
            
            AlertResponse created = alertService.create(alert);
            
            try {
                messagingTemplate.convertAndSend("/topic/alerts/" + state.getWellId(), created);
            } catch (Exception e) {
                log.error("Failed to push alert to WebSocket", e);
            }
            
            log.info("Generated Alert for Well {}", state.getWellId());
        } catch (Exception e) {
            log.error("Failed to generate alert for well {}: {}", state.getWellId(), e.getMessage());
        }
    }
}
