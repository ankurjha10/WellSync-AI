package com.wellsync.ai.controller;

import com.wellsync.ai.dto.SensorConfigRequest;
import com.wellsync.ai.dto.SensorConfigResponse;
import com.wellsync.ai.service.SensorConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sensor-configs")
@RequiredArgsConstructor
@Tag(name = "Sensor Config", description = "Sensor configuration management endpoints")
public class SensorConfigController {

    private final SensorConfigService sensorConfigService;

    @GetMapping
    @Operation(summary = "Get sensor configs by well", description = "Retrieves all sensor configurations for a given well.")
    @ApiResponse(responseCode = "200", description = "Sensor configs retrieved successfully")
    public ResponseEntity<List<SensorConfigResponse>> getAllByWellId(
            @Parameter(description = "Well UUID to filter sensor configs by", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(sensorConfigService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get sensor config by ID", description = "Retrieves a single sensor configuration by its UUID.")
    @ApiResponse(responseCode = "200", description = "Sensor config found")
    @ApiResponse(responseCode = "404", description = "Sensor config not found")
    public ResponseEntity<SensorConfigResponse> getById(
            @Parameter(description = "Sensor Config UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(sensorConfigService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a sensor config", description = "Creates a new sensor configuration for a well.")
    @ApiResponse(responseCode = "201", description = "Sensor config created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<SensorConfigResponse> create(@Valid @RequestBody SensorConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorConfigService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a sensor config", description = "Updates an existing sensor configuration by its UUID.")
    @ApiResponse(responseCode = "200", description = "Sensor config updated successfully")
    @ApiResponse(responseCode = "404", description = "Sensor config or referenced well not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<SensorConfigResponse> update(
            @Parameter(description = "Sensor Config UUID") @PathVariable UUID id,
            @Valid @RequestBody SensorConfigRequest request) {
        return ResponseEntity.ok(sensorConfigService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sensor config", description = "Deletes a sensor configuration by its UUID.")
    @ApiResponse(responseCode = "204", description = "Sensor config deleted successfully")
    @ApiResponse(responseCode = "404", description = "Sensor config not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Sensor Config UUID") @PathVariable UUID id) {
        sensorConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
