package com.wellsync.ai.controller;

import com.wellsync.ai.dto.SystemEventRequest;
import com.wellsync.ai.dto.SystemEventResponse;
import com.wellsync.ai.service.SystemEventService;
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
@RequestMapping("/api/v1/system-events")
@RequiredArgsConstructor
@Tag(name = "System Event", description = "System event logging and audit trail endpoints")
public class SystemEventController {

    private final SystemEventService systemEventService;

    @GetMapping
    @Operation(summary = "Get all system events or filter by well")
    @ApiResponse(responseCode = "200", description = "System events retrieved successfully")
    public ResponseEntity<List<SystemEventResponse>> getAll(
            @Parameter(description = "Optional Well UUID to filter by")
            @RequestParam(required = false) UUID wellId) {
        if (wellId != null) {
            return ResponseEntity.ok(systemEventService.getAllByWellId(wellId));
        }
        return ResponseEntity.ok(systemEventService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get system event by ID")
    @ApiResponse(responseCode = "200", description = "System event found")
    @ApiResponse(responseCode = "404", description = "System event not found")
    public ResponseEntity<SystemEventResponse> getById(
            @Parameter(description = "System Event UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(systemEventService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a system event")
    @ApiResponse(responseCode = "201", description = "System event created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<SystemEventResponse> create(@Valid @RequestBody SystemEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(systemEventService.create(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a system event")
    @ApiResponse(responseCode = "204", description = "System event deleted successfully")
    @ApiResponse(responseCode = "404", description = "System event not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "System Event UUID") @PathVariable UUID id) {
        systemEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
