package com.wellsync.ai.controller;

import com.wellsync.ai.dto.CssCycleRequest;
import com.wellsync.ai.dto.CssCycleResponse;
import com.wellsync.ai.service.CssCycleService;
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
@RequestMapping("/api/v1/css-cycles")
@RequiredArgsConstructor
@Tag(name = "CSS Cycle", description = "Cyclic Steam Stimulation cycle management endpoints")
public class CssCycleController {

    private final CssCycleService cssCycleService;

    @GetMapping
    @Operation(summary = "Get CSS cycles by well", description = "Retrieves all CSS cycles for a given well, ordered by cycle number descending.")
    @ApiResponse(responseCode = "200", description = "CSS cycles retrieved successfully")
    public ResponseEntity<List<CssCycleResponse>> getAllByWellId(
            @Parameter(description = "Well UUID to filter cycles by", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(cssCycleService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get CSS cycle by ID", description = "Retrieves a single CSS cycle by its UUID.")
    @ApiResponse(responseCode = "200", description = "CSS cycle found")
    @ApiResponse(responseCode = "404", description = "CSS cycle not found")
    public ResponseEntity<CssCycleResponse> getById(
            @Parameter(description = "CSS Cycle UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(cssCycleService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a CSS cycle", description = "Creates a new CSS cycle for a well.")
    @ApiResponse(responseCode = "201", description = "CSS cycle created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<CssCycleResponse> create(@Valid @RequestBody CssCycleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cssCycleService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a CSS cycle", description = "Updates an existing CSS cycle by its UUID.")
    @ApiResponse(responseCode = "200", description = "CSS cycle updated successfully")
    @ApiResponse(responseCode = "404", description = "CSS cycle or referenced well not found")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<CssCycleResponse> update(
            @Parameter(description = "CSS Cycle UUID") @PathVariable UUID id,
            @Valid @RequestBody CssCycleRequest request) {
        return ResponseEntity.ok(cssCycleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a CSS cycle", description = "Deletes a CSS cycle by its UUID.")
    @ApiResponse(responseCode = "204", description = "CSS cycle deleted successfully")
    @ApiResponse(responseCode = "404", description = "CSS cycle not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "CSS Cycle UUID") @PathVariable UUID id) {
        cssCycleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
