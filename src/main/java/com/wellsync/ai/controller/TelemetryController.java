package com.wellsync.ai.controller;

import com.wellsync.ai.digitaltwin.DigitalTwinStateService;
import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.TelemetryData;
import com.wellsync.ai.recommendation.RecommendationEngine;
import com.wellsync.ai.risk.RiskEngine;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    private final com.wellsync.ai.control.ControlSafetyEngine controlSafetyEngine;
    private final RiskEngine riskEngine;
    private final RecommendationEngine recommendationEngine;

    @PostMapping
    @Operation(summary = "Ingest real-time telemetry", description = "Receives telemetry, updates the Digital Twin state, runs the risk engine, and returns any pending control commands for the simulator.")
    @ApiResponse(responseCode = "200", description = "Telemetry processed successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request payload")
    public ResponseEntity<java.util.Map<String, Object>> ingestTelemetry(
            @Valid @RequestBody TelemetryData request) {
        
        // 1. Process telemetry and update digital twin state
        WellDigitalTwinState updatedState = digitalTwinStateService.updateState(request);
        List<String> riskFactors = riskEngine.evaluateRisk(updatedState);
        recommendationEngine.evaluateRecommendations(updatedState, riskFactors);
        
        // 2. Check if there are any pending commands for the simulator to execute
        java.util.Map<String, Object> response = new java.util.HashMap<>();
        response.put("state", updatedState);
        
        controlSafetyEngine.getNextPendingCommand(request.getWellId()).ifPresent(cmd -> {
            response.put("pendingCommand", cmd);
            // Auto mark as executed since the simulator picked it up
            controlSafetyEngine.markCommandExecuted(cmd.getId());
        });
        
        return ResponseEntity.ok(response);
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
