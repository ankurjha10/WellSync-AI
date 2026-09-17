package com.wellsync.ai.controller;

import com.wellsync.ai.dto.SrpOperatingConfigRequest;
import com.wellsync.ai.dto.SrpOperatingConfigResponse;
import com.wellsync.ai.service.SrpOperatingConfigService;
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
@RequestMapping("/api/v1/srp-operating-configs")
@RequiredArgsConstructor
@Tag(name = "SRP Operating Config", description = "SRP operating configuration management endpoints")
public class SrpOperatingConfigController {

    private final SrpOperatingConfigService srpOperatingConfigService;

    @GetMapping
    @Operation(summary = "Get all SRP operating configs", description = "Retrieves a list of all SRP operating configurations.")
    @ApiResponse(responseCode = "200", description = "Configs retrieved successfully")
    public ResponseEntity<List<SrpOperatingConfigResponse>> getAll() {
        return ResponseEntity.ok(srpOperatingConfigService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SRP operating config by ID", description = "Retrieves a single SRP operating config by its UUID.")
    @ApiResponse(responseCode = "200", description = "Config found")
    @ApiResponse(responseCode = "404", description = "Config not found")
    public ResponseEntity<SrpOperatingConfigResponse> getById(
            @Parameter(description = "SRP Operating Config UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(srpOperatingConfigService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create an SRP operating config", description = "Creates a new operating config for an SRP system. Only one config per SRP system is allowed.")
    @ApiResponse(responseCode = "201", description = "Config created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced SRP system not found")
    @ApiResponse(responseCode = "409", description = "Config already exists for the specified SRP system")
    public ResponseEntity<SrpOperatingConfigResponse> create(@Valid @RequestBody SrpOperatingConfigRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(srpOperatingConfigService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an SRP operating config", description = "Updates an existing SRP operating config by its UUID.")
    @ApiResponse(responseCode = "200", description = "Config updated successfully")
    @ApiResponse(responseCode = "404", description = "Config or referenced SRP system not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "Config already exists for the target SRP system")
    public ResponseEntity<SrpOperatingConfigResponse> update(
            @Parameter(description = "SRP Operating Config UUID") @PathVariable UUID id,
            @Valid @RequestBody SrpOperatingConfigRequest request) {
        return ResponseEntity.ok(srpOperatingConfigService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an SRP operating config", description = "Deletes an SRP operating config by its UUID.")
    @ApiResponse(responseCode = "204", description = "Config deleted successfully")
    @ApiResponse(responseCode = "404", description = "Config not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "SRP Operating Config UUID") @PathVariable UUID id) {
        srpOperatingConfigService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
