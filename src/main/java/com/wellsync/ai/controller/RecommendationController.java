package com.wellsync.ai.controller;

import com.wellsync.ai.dto.RecommendationRequest;
import com.wellsync.ai.dto.RecommendationResponse;
import com.wellsync.ai.service.RecommendationService;
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
@RequestMapping("/api/v1/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendation", description = "AI recommendation management endpoints")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    @Operation(summary = "Get recommendations by well", description = "Retrieves all AI recommendations for a given well.")
    @ApiResponse(responseCode = "200", description = "Recommendations retrieved successfully")
    public ResponseEntity<List<RecommendationResponse>> getAllByWellId(
            @Parameter(description = "Well UUID", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(recommendationService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recommendation by ID")
    @ApiResponse(responseCode = "200", description = "Recommendation found")
    @ApiResponse(responseCode = "404", description = "Recommendation not found")
    public ResponseEntity<RecommendationResponse> getById(
            @Parameter(description = "Recommendation UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(recommendationService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a recommendation")
    @ApiResponse(responseCode = "201", description = "Recommendation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<RecommendationResponse> create(@Valid @RequestBody RecommendationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.create(request));
    }

    @PatchMapping("/{id}/acted")
    @Operation(summary = "Mark recommendation as acted upon", description = "Sets the actedAt timestamp to now.")
    @ApiResponse(responseCode = "200", description = "Recommendation marked as acted")
    @ApiResponse(responseCode = "404", description = "Recommendation not found")
    public ResponseEntity<RecommendationResponse> markActed(
            @Parameter(description = "Recommendation UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(recommendationService.markActed(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a recommendation")
    @ApiResponse(responseCode = "200", description = "Recommendation updated successfully")
    @ApiResponse(responseCode = "404", description = "Recommendation or well not found")
    public ResponseEntity<RecommendationResponse> update(
            @Parameter(description = "Recommendation UUID") @PathVariable UUID id,
            @Valid @RequestBody RecommendationRequest request) {
        return ResponseEntity.ok(recommendationService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recommendation")
    @ApiResponse(responseCode = "204", description = "Recommendation deleted successfully")
    @ApiResponse(responseCode = "404", description = "Recommendation not found")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Recommendation UUID") @PathVariable UUID id) {
        recommendationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
