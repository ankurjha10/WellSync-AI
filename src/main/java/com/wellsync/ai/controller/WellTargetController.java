package com.wellsync.ai.controller;

import com.wellsync.ai.dto.WellTargetRequest;
import com.wellsync.ai.dto.WellTargetResponse;
import com.wellsync.ai.service.WellTargetService;
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
@RequestMapping("/api/v1/well-targets")
@RequiredArgsConstructor
@Tag(name = "Well Target", description = "Well target/threshold management endpoints")
public class WellTargetController {

    private final WellTargetService wellTargetService;

    @GetMapping
    @Operation(summary = "Get all well targets", description = "Retrieves a list of all well targets.")
    @ApiResponse(responseCode = "200", description = "Well targets retrieved successfully")
    public ResponseEntity<List<WellTargetResponse>> getAll() {
        return ResponseEntity.ok(wellTargetService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get well target by ID", description = "Retrieves a single well target by its UUID.")
    @ApiResponse(responseCode = "200", description = "Well target found")
    @ApiResponse(responseCode = "404", description = "Well target not found")
    public ResponseEntity<WellTargetResponse> getById(
            @Parameter(description = "Well Target UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(wellTargetService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a well target", description = "Creates a new well target. Only one target per well is allowed.")
    @ApiResponse(responseCode = "201", description = "Well target created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    @ApiResponse(responseCode = "409", description = "Well target already exists for the specified well")
    public ResponseEntity<WellTargetResponse> create(@Valid @RequestBody WellTargetRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wellTargetService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a well target", description = "Updates an existing well target by its UUID.")
    @ApiResponse(responseCode = "200", description = "Well target updated successfully")
    @ApiResponse(responseCode = "404", description = "Well target or referenced well not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "Well target already exists for the target well")
    public ResponseEntity<WellTargetResponse> update(
            @Parameter(description = "Well Target UUID") @PathVariable UUID id,
            @Valid @RequestBody WellTargetRequest request) {
        return ResponseEntity.ok(wellTargetService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a well target", description = "Deletes a well target by its UUID.")
    @ApiResponse(responseCode = "204", description = "Well target deleted successfully")
    @ApiResponse(responseCode = "404", description = "Well target not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Well Target UUID") @PathVariable UUID id) {
        wellTargetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
