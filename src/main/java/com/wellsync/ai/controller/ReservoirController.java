package com.wellsync.ai.controller;

import com.wellsync.ai.dto.ReservoirRequest;
import com.wellsync.ai.dto.ReservoirResponse;
import com.wellsync.ai.service.ReservoirService;
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
@RequestMapping("/api/v1/reservoirs")
@RequiredArgsConstructor
@Tag(name = "Reservoir", description = "Reservoir management endpoints")
public class ReservoirController {

    private final ReservoirService reservoirService;

    @GetMapping
    @Operation(summary = "Get all reservoirs", description = "Retrieves a list of all reservoirs.")
    @ApiResponse(responseCode = "200", description = "Reservoirs retrieved successfully")
    public ResponseEntity<List<ReservoirResponse>> getAll() {
        return ResponseEntity.ok(reservoirService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get reservoir by ID", description = "Retrieves a single reservoir by its UUID.")
    @ApiResponse(responseCode = "200", description = "Reservoir found")
    @ApiResponse(responseCode = "404", description = "Reservoir not found")
    public ResponseEntity<ReservoirResponse> getById(
            @Parameter(description = "Reservoir UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(reservoirService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a reservoir", description = "Creates a new reservoir record.")
    @ApiResponse(responseCode = "201", description = "Reservoir created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<ReservoirResponse> create(@Valid @RequestBody ReservoirRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservoirService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a reservoir", description = "Updates an existing reservoir by its UUID.")
    @ApiResponse(responseCode = "200", description = "Reservoir updated successfully")
    @ApiResponse(responseCode = "404", description = "Reservoir not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<ReservoirResponse> update(
            @Parameter(description = "Reservoir UUID") @PathVariable UUID id,
            @Valid @RequestBody ReservoirRequest request) {
        return ResponseEntity.ok(reservoirService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a reservoir", description = "Deletes a reservoir by its UUID.")
    @ApiResponse(responseCode = "204", description = "Reservoir deleted successfully")
    @ApiResponse(responseCode = "404", description = "Reservoir not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Reservoir UUID") @PathVariable UUID id) {
        reservoirService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
