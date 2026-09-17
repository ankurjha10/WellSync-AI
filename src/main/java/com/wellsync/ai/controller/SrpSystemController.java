package com.wellsync.ai.controller;

import com.wellsync.ai.dto.SrpSystemRequest;
import com.wellsync.ai.dto.SrpSystemResponse;
import com.wellsync.ai.service.SrpSystemService;
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
@RequestMapping("/api/v1/srp-systems")
@RequiredArgsConstructor
@Tag(name = "SRP System", description = "Sucker Rod Pump system management endpoints")
public class SrpSystemController {

    private final SrpSystemService srpSystemService;

    @GetMapping
    @Operation(summary = "Get all SRP systems", description = "Retrieves a list of all SRP systems.")
    @ApiResponse(responseCode = "200", description = "SRP systems retrieved successfully")
    public ResponseEntity<List<SrpSystemResponse>> getAll() {
        return ResponseEntity.ok(srpSystemService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get SRP system by ID", description = "Retrieves a single SRP system by its UUID.")
    @ApiResponse(responseCode = "200", description = "SRP system found")
    @ApiResponse(responseCode = "404", description = "SRP system not found")
    public ResponseEntity<SrpSystemResponse> getById(
            @Parameter(description = "SRP System UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(srpSystemService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create an SRP system", description = "Creates a new SRP system for a well. Only one SRP system per well is allowed.")
    @ApiResponse(responseCode = "201", description = "SRP system created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    @ApiResponse(responseCode = "409", description = "SRP system already exists for the specified well")
    public ResponseEntity<SrpSystemResponse> create(@Valid @RequestBody SrpSystemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(srpSystemService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an SRP system", description = "Updates an existing SRP system by its UUID.")
    @ApiResponse(responseCode = "200", description = "SRP system updated successfully")
    @ApiResponse(responseCode = "404", description = "SRP system or referenced well not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "SRP system already exists for the target well")
    public ResponseEntity<SrpSystemResponse> update(
            @Parameter(description = "SRP System UUID") @PathVariable UUID id,
            @Valid @RequestBody SrpSystemRequest request) {
        return ResponseEntity.ok(srpSystemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an SRP system", description = "Deletes an SRP system by its UUID.")
    @ApiResponse(responseCode = "204", description = "SRP system deleted successfully")
    @ApiResponse(responseCode = "404", description = "SRP system not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "SRP System UUID") @PathVariable UUID id) {
        srpSystemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
