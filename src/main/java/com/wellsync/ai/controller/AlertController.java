package com.wellsync.ai.controller;

import com.wellsync.ai.dto.AlertRequest;
import com.wellsync.ai.dto.AlertResponse;
import com.wellsync.ai.service.AlertService;
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
@RequestMapping("/api/v1/alerts")
@RequiredArgsConstructor
@Tag(name = "Alert", description = "Alert management endpoints")
public class AlertController {

    private final AlertService alertService;

    @GetMapping
    @Operation(summary = "Get alerts by well", description = "Retrieves all alerts for a given well, ordered by most recent first.")
    @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully")
    public ResponseEntity<List<AlertResponse>> getAllByWellId(
            @Parameter(description = "Well UUID", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(alertService.getAllByWellId(wellId));
    }

    @GetMapping("/unacknowledged")
    @Operation(summary = "Get unacknowledged alerts by well")
    @ApiResponse(responseCode = "200", description = "Unacknowledged alerts retrieved")
    public ResponseEntity<List<AlertResponse>> getUnacknowledgedByWellId(
            @Parameter(description = "Well UUID", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(alertService.getUnacknowledgedByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get alert by ID")
    @ApiResponse(responseCode = "200", description = "Alert found")
    @ApiResponse(responseCode = "404", description = "Alert not found")
    public ResponseEntity<AlertResponse> getById(
            @Parameter(description = "Alert UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(alertService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create an alert")
    @ApiResponse(responseCode = "201", description = "Alert created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<AlertResponse> create(@Valid @RequestBody AlertRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertService.create(request));
    }

    @PatchMapping("/{id}/acknowledge")
    @Operation(summary = "Acknowledge an alert", description = "Marks an alert as acknowledged by a user.")
    @ApiResponse(responseCode = "200", description = "Alert acknowledged successfully")
    @ApiResponse(responseCode = "404", description = "Alert or user not found")
    public ResponseEntity<AlertResponse> acknowledge(
            @Parameter(description = "Alert UUID") @PathVariable UUID id,
            @Parameter(description = "User UUID who is acknowledging", required = true)
            @RequestParam UUID userId) {
        return ResponseEntity.ok(alertService.acknowledge(id, userId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an alert")
    @ApiResponse(responseCode = "200", description = "Alert updated successfully")
    @ApiResponse(responseCode = "404", description = "Alert or well not found")
    public ResponseEntity<AlertResponse> update(
            @Parameter(description = "Alert UUID") @PathVariable UUID id,
            @Valid @RequestBody AlertRequest request) {
        return ResponseEntity.ok(alertService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an alert")
    @ApiResponse(responseCode = "204", description = "Alert deleted successfully")
    @ApiResponse(responseCode = "404", description = "Alert not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Alert UUID") @PathVariable UUID id) {
        alertService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
