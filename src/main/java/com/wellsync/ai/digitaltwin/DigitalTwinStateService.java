package com.wellsync.ai.digitaltwin;

import com.wellsync.ai.dto.TelemetryData;
import com.wellsync.ai.entity.enums.RiskLevel;
import com.wellsync.ai.entity.enums.SystemStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class DigitalTwinStateService {

    private final ConcurrentHashMap<UUID, WellDigitalTwinState> wellStates = new ConcurrentHashMap<>();

    public WellDigitalTwinState getState(UUID wellId) {
        return wellStates.get(wellId);
    }

    public Collection<WellDigitalTwinState> getAllStates() {
        return wellStates.values();
    }

    public WellDigitalTwinState updateState(TelemetryData telemetry) {
        UUID wellId = telemetry.getWellId();
        
        WellDigitalTwinState state = wellStates.computeIfAbsent(wellId, id -> 
                WellDigitalTwinState.builder()
                        .wellId(id)
                        .systemStatus(SystemStatus.NORMAL)
                        .riskLevel(RiskLevel.HEALTHY)
                        .currentRiskScore(0)
                        .build()
        );

        if (telemetry.getTemperatureC() != null) state.setTemperatureC(telemetry.getTemperatureC());
        if (telemetry.getPressurePsi() != null) state.setPressurePsi(telemetry.getPressurePsi());
        if (telemetry.getViscosityCp() != null) state.setViscosityCp(telemetry.getViscosityCp());
        
        if (telemetry.getPumpRpm() != null) state.setPumpRpm(telemetry.getPumpRpm());
        if (telemetry.getRodLoadLbs() != null) state.setRodLoadLbs(telemetry.getRodLoadLbs());
        if (telemetry.getSpm() != null) state.setSpm(telemetry.getSpm());
        if (telemetry.getVfdFrequencyHz() != null) state.setVfdFrequencyHz(telemetry.getVfdFrequencyHz());
        if (telemetry.getStrokeLengthIn() != null) state.setStrokeLengthIn(telemetry.getStrokeLengthIn());
        if (telemetry.getPumpEfficiencyPercent() != null) state.setPumpEfficiencyPercent(telemetry.getPumpEfficiencyPercent());

        if (telemetry.getSteamPressurePsi() != null) state.setSteamPressurePsi(telemetry.getSteamPressurePsi());
        if (telemetry.getSteamTemperatureC() != null) state.setSteamTemperatureC(telemetry.getSteamTemperatureC());
        
        if (telemetry.getProductionRateBopd() != null) state.setProductionRateBopd(telemetry.getProductionRateBopd());
        
        state.setLastUpdatedAt(telemetry.getTimestamp());
        
        log.debug("Updated Digital Twin State for Well {}: {}", wellId, state);

        return state;
    }
}
