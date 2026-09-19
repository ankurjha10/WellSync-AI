package com.wellsync.ai.controller;

import com.wellsync.ai.dto.FailureEventRequest;
import com.wellsync.ai.dto.FailureEventResponse;
import com.wellsync.ai.service.FailureEventService;
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
@RequestMapping("/api/v1/failure-events")
@RequiredArgsConstructor
@Tag(name = "Failure Event", description = "Rod failure and pump unsetting event management")
public class FailureEventController {

    private final FailureEventService failureEventService;

    @GetMapping
    @Operation(summary = "Get failure events by well", description = "Retrieves all failure events for a given well.")
    @ApiResponse(responseCode = "200", description = "Failure events retrieved successfully")
    public ResponseEntity<List<FailureEventResponse>> getAllByWellId(
            @Parameter(description = "Well UUID", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(failureEventService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get failure event by ID")
    @ApiResponse(responseCode = "200", description = "Failure event found")
    @ApiResponse(responseCode = "404", description = "Failure event not found")
    public ResponseEntity<FailureEventResponse> getById(
            @Parameter(description = "Failure Event UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(failureEventService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a failure event")
    @ApiResponse(responseCode = "201", description = "Failure event created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<FailureEventResponse> create(@Valid @RequestBody FailureEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(failureEventService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a failure event")
    @ApiResponse(responseCode = "200", description = "Failure event updated successfully")
    @ApiResponse(responseCode = "404", description = "Failure event or well not found")
    public ResponseEntity<FailureEventResponse> update(
            @Parameter(description = "Failure Event UUID") @PathVariable UUID id,
            @Valid @RequestBody FailureEventRequest request) {
        return ResponseEntity.ok(failureEventService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a failure event")
    @ApiResponse(responseCode = "204", description = "Failure event deleted successfully")
    @ApiResponse(responseCode = "404", description = "Failure event not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Failure Event UUID") @PathVariable UUID id) {
        failureEventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
