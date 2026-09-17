package com.wellsync.ai.controller;

import com.wellsync.ai.dto.CompletionRequest;
import com.wellsync.ai.dto.CompletionResponse;
import com.wellsync.ai.service.CompletionService;
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
@RequestMapping("/api/v1/completions")
@RequiredArgsConstructor
@Tag(name = "Completion", description = "Well completion management endpoints")
public class CompletionController {

    private final CompletionService completionService;

    @GetMapping
    @Operation(summary = "Get all completions", description = "Retrieves a list of all well completions.")
    @ApiResponse(responseCode = "200", description = "Completions retrieved successfully")
    public ResponseEntity<List<CompletionResponse>> getAll() {
        return ResponseEntity.ok(completionService.getAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get completion by ID", description = "Retrieves a single completion by its UUID.")
    @ApiResponse(responseCode = "200", description = "Completion found")
    @ApiResponse(responseCode = "404", description = "Completion not found")
    public ResponseEntity<CompletionResponse> getById(
            @Parameter(description = "Completion UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(completionService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a completion", description = "Creates a new completion for a well. Only one completion per well is allowed.")
    @ApiResponse(responseCode = "201", description = "Completion created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    @ApiResponse(responseCode = "409", description = "Completion already exists for the specified well")
    public ResponseEntity<CompletionResponse> create(@Valid @RequestBody CompletionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(completionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a completion", description = "Updates an existing completion by its UUID.")
    @ApiResponse(responseCode = "200", description = "Completion updated successfully")
    @ApiResponse(responseCode = "404", description = "Completion or referenced well not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "409", description = "Completion already exists for the target well")
    public ResponseEntity<CompletionResponse> update(
            @Parameter(description = "Completion UUID") @PathVariable UUID id,
            @Valid @RequestBody CompletionRequest request) {
        return ResponseEntity.ok(completionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a completion", description = "Deletes a completion by its UUID.")
    @ApiResponse(responseCode = "204", description = "Completion deleted successfully")
    @ApiResponse(responseCode = "404", description = "Completion not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Completion UUID") @PathVariable UUID id) {
        completionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
