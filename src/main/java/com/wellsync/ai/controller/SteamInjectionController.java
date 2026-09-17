package com.wellsync.ai.controller;

import com.wellsync.ai.dto.SteamInjectionRequest;
import com.wellsync.ai.dto.SteamInjectionResponse;
import com.wellsync.ai.service.SteamInjectionService;
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
@RequestMapping("/api/v1/steam-injections")
@RequiredArgsConstructor
@Tag(name = "Steam Injection", description = "Steam injection record management endpoints")
public class SteamInjectionController {

    private final SteamInjectionService steamInjectionService;

    @GetMapping
    @Operation(summary = "Get steam injections by CSS cycle", description = "Retrieves all steam injection records for a given CSS cycle.")
    @ApiResponse(responseCode = "200", description = "Steam injections retrieved successfully")
    public ResponseEntity<List<SteamInjectionResponse>> getAllByCssCycleId(
            @Parameter(description = "CSS Cycle UUID to filter injections by", required = true)
            @RequestParam UUID cssCycleId) {
        return ResponseEntity.ok(steamInjectionService.getAllByCssCycleId(cssCycleId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get steam injection by ID", description = "Retrieves a single steam injection record by its UUID.")
    @ApiResponse(responseCode = "200", description = "Steam injection found")
    @ApiResponse(responseCode = "404", description = "Steam injection not found")
    public ResponseEntity<SteamInjectionResponse> getById(
            @Parameter(description = "Steam Injection UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(steamInjectionService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a steam injection", description = "Creates a new steam injection record for a CSS cycle.")
    @ApiResponse(responseCode = "201", description = "Steam injection created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced CSS cycle not found")
    public ResponseEntity<SteamInjectionResponse> create(@Valid @RequestBody SteamInjectionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(steamInjectionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a steam injection", description = "Updates an existing steam injection record by its UUID.")
    @ApiResponse(responseCode = "200", description = "Steam injection updated successfully")
    @ApiResponse(responseCode = "404", description = "Steam injection or referenced CSS cycle not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<SteamInjectionResponse> update(
            @Parameter(description = "Steam Injection UUID") @PathVariable UUID id,
            @Valid @RequestBody SteamInjectionRequest request) {
        return ResponseEntity.ok(steamInjectionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a steam injection", description = "Deletes a steam injection record by its UUID.")
    @ApiResponse(responseCode = "204", description = "Steam injection deleted successfully")
    @ApiResponse(responseCode = "404", description = "Steam injection not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Steam Injection UUID") @PathVariable UUID id) {
        steamInjectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
