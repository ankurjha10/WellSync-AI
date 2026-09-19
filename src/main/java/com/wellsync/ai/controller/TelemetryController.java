package com.wellsync.ai.controller;

import com.wellsync.ai.digitaltwin.DigitalTwinStateService;
import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.TelemetryData;
import com.wellsync.ai.recommendation.RecommendationEngine;
import com.wellsync.ai.risk.RiskEngine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Telemetry & Digital Twin", description = "Ingest real-time telemetry and query Digital Twin state")
public class TelemetryController {

    private final DigitalTwinStateService digitalTwinStateService;
    private final RiskEngine riskEngine;
    private final RecommendationEngine recommendationEngine;

    @PostMapping
    @Operation(summary = "Ingest new telemetry", description = "Updates the Digital Twin state and runs the Risk & Recommendation engines.")
    public ResponseEntity<WellDigitalTwinState> ingestTelemetry(@Valid @RequestBody TelemetryData telemetry) {
        log.debug("Received telemetry for well {}: {}", telemetry.getWellId(), telemetry);

        WellDigitalTwinState state = digitalTwinStateService.updateState(telemetry);

        List<String> riskFactors = riskEngine.evaluateRisk(state);

        recommendationEngine.evaluateRecommendations(state, riskFactors);

        return ResponseEntity.ok(state);
    }

    @GetMapping("/state")
    @Operation(summary = "Get all active Digital Twin states")
    public ResponseEntity<Collection<WellDigitalTwinState>> getAllStates() {
        return ResponseEntity.ok(digitalTwinStateService.getAllStates());
    }

    @GetMapping("/state/{wellId}")
    @Operation(summary = "Get Digital Twin state by Well ID")
    public ResponseEntity<WellDigitalTwinState> getStateByWellId(@PathVariable UUID wellId) {
        WellDigitalTwinState state = digitalTwinStateService.getState(wellId);
        if (state == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(state);
    }
}
