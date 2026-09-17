package com.wellsync.ai.controller;

import com.wellsync.ai.dto.WellRequest;
import com.wellsync.ai.dto.WellResponse;
import com.wellsync.ai.service.WellService;
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
@RequestMapping("/api/v1/wells")
@RequiredArgsConstructor
@Tag(name = "Well", description = "Well management endpoints")
public class WellController {

    private final WellService wellService;

    @GetMapping
    @Operation(summary = "Get all wells", description = "Retrieves a list of all wells.")
    @ApiResponse(responseCode = "200", description = "Wells retrieved successfully")
    public ResponseEntity<List<WellResponse>> getAll() {
        return ResponseEntity.ok(wellService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get well by ID", description = "Retrieves a single well by its UUID.")
    @ApiResponse(responseCode = "200", description = "Well found")
    @ApiResponse(responseCode = "404", description = "Well not found")
    public ResponseEntity<WellResponse> getById(
            @Parameter(description = "Well UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(wellService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a well", description = "Creates a new well. Requires a valid reservoirId.")
    @ApiResponse(responseCode = "201", description = "Well created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced reservoir not found")
    @ApiResponse(responseCode = "409", description = "Well code already exists")
    public ResponseEntity<WellResponse> create(@Valid @RequestBody WellRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(wellService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a well", description = "Updates an existing well by its UUID.")
    @ApiResponse(responseCode = "200", description = "Well updated successfully")
    @ApiResponse(responseCode = "404", description = "Well or referenced reservoir not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "Well code already exists")
    public ResponseEntity<WellResponse> update(
            @Parameter(description = "Well UUID") @PathVariable UUID id,
            @Valid @RequestBody WellRequest request) {
        return ResponseEntity.ok(wellService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a well", description = "Deletes a well by its UUID.")
    @ApiResponse(responseCode = "204", description = "Well deleted successfully")
    @ApiResponse(responseCode = "404", description = "Well not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Well UUID") @PathVariable UUID id) {
        wellService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
