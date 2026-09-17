package com.wellsync.ai.controller;

import com.wellsync.ai.dto.ProductionRecordRequest;
import com.wellsync.ai.dto.ProductionRecordResponse;
import com.wellsync.ai.service.ProductionRecordService;
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
@RequestMapping("/api/v1/production-records")
@RequiredArgsConstructor
@Tag(name = "Production Record", description = "Production record management endpoints (append-only)")
public class ProductionRecordController {

    private final ProductionRecordService productionRecordService;

    @GetMapping
    @Operation(summary = "Get production records by well", description = "Retrieves all production records for a given well, ordered by recordedAt descending.")
    @ApiResponse(responseCode = "200", description = "Production records retrieved successfully")
    public ResponseEntity<List<ProductionRecordResponse>> getAllByWellId(
            @Parameter(description = "Well UUID to filter production records by", required = true)
            @RequestParam UUID wellId) {
        return ResponseEntity.ok(productionRecordService.getAllByWellId(wellId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get production record by ID", description = "Retrieves a single production record by its UUID.")
    @ApiResponse(responseCode = "200", description = "Production record found")
    @ApiResponse(responseCode = "404", description = "Production record not found")
    public ResponseEntity<ProductionRecordResponse> getById(
            @Parameter(description = "Production Record UUID") @PathVariable UUID id) {
        return ResponseEntity.ok(productionRecordService.getById(id));
    }

    @PostMapping
    @Operation(summary = "Create a production record", description = "Creates a new production record for a well. This is an append-only resource; update and delete are not supported.")
    @ApiResponse(responseCode = "201", description = "Production record created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    @ApiResponse(responseCode = "404", description = "Referenced well not found")
    public ResponseEntity<ProductionRecordResponse> create(@Valid @RequestBody ProductionRecordRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productionRecordService.create(request));
    }
}
