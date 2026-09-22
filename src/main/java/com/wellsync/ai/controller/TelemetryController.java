package com.wellsync.ai.controller;

import com.wellsync.ai.dto.TelemetryData;
import com.wellsync.ai.repository.TelemetryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/telemetry")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allows Next.js frontend to call this API during local dev
public class TelemetryController {

    private final TelemetryRepository telemetryRepository;

    @GetMapping("/history/{wellId}")
    public ResponseEntity<List<TelemetryData>> getHistory(
            @PathVariable UUID wellId,
            @RequestParam(defaultValue = "1h") String range) {
        
        List<TelemetryData> history = telemetryRepository.getHistory(wellId, range);
        return ResponseEntity.ok(history);
    }
}
