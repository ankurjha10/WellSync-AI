package com.wellsync.ai.controller;

import com.wellsync.ai.dto.ControlCommandRequest;
import com.wellsync.ai.dto.ControlCommandResponse;
import com.wellsync.ai.service.ControlCommandService;
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
@RequestMapping("/api/v1/control-commands")
@RequiredArgsConstructor
@Tag(name = "Control Command", description = "VFD/SRP control command management endpoints")
public class ControlCommandController {

    private final ControlCommandService controlCommandService;
    private final com.wellsync.ai.control.ControlSafetyEngine controlSafetyEngine;

    @GetMapping
    @Operation(summary = "Get control commands by well", description = "Retrieves all control commands for a given well.")
    @ApiResponse(responseCode = "200", description = "Control commands retrieved successfully")
    public ResponseEntity<List<ControlCommandResponse>> getAllByWellId(
            @Parameter(description = "Well UUID", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(controlCommandService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get control command by ID")
    @ApiResponse(responseCode = "200", description = "Control command found")
    @ApiResponse(responseCode = "404", description = "Control command not found")
    public ResponseEntity<ControlCommandResponse> getById(
            @Parameter(description = "Control Command UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(controlCommandService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a control command")
    @ApiResponse(responseCode = "201", description = "Control command created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well, recommendation, or user not found")
    public ResponseEntity<ControlCommandResponse> create(@Valid @RequestBody ControlCommandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(controlCommandService.create(request));
    }

    @PatchMapping("/{id}/execute")
    @Operation(summary = "Mark command as executed", description = "Sets the executedAt timestamp to now.")
    @ApiResponse(responseCode = "200", description = "Command marked as executed")
    @ApiResponse(responseCode = "404", description = "Control command not found")
    public ResponseEntity<ControlCommandResponse> markExecuted(
            @Parameter(description = "Control Command UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(controlCommandService.markExecuted(id));
    }

    @PostMapping("/execute-recommendation")
    @Operation(summary = "Execute a safety-validated control command from the frontend")
    public ResponseEntity<?> executeValidatedCommand(@Valid @RequestBody ControlCommandRequest request) {
        try {
            return ResponseEntity.ok(controlSafetyEngine.requestCommandExecution(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a control command")
    @ApiResponse(responseCode = "200", description = "Control command updated successfully")
    @ApiResponse(responseCode = "404", description = "Control command or referenced entity not found")
    public ResponseEntity<ControlCommandResponse> update(
            @Parameter(description = "Control Command UUID") @PathVariable UUID id,
            @Valid @RequestBody ControlCommandRequest request) {
        return ResponseEntity.ok(controlCommandService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a control command")
    @ApiResponse(responseCode = "204", description = "Control command deleted successfully")
    @ApiResponse(responseCode = "404", description = "Control command not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Control Command UUID") @PathVariable UUID id) {
        controlCommandService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
