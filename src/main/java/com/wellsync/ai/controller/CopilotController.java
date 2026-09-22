package com.wellsync.ai.controller;

import com.wellsync.ai.dto.CopilotChatRequest;
import com.wellsync.ai.dto.CopilotResponse;
import com.wellsync.ai.service.CopilotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/copilot")
@RequiredArgsConstructor
@Tag(name = "Copilot", description = "AI Operator Copilot endpoints")
public class CopilotController {

    private final CopilotService copilotService;

    @GetMapping("/explain-alert/{alertId}")
    @Operation(
            summary = "Explain an alert",
            description = "Uses AI to generate a root-cause explanation for a given alert, "
                    + "incorporating the current Digital Twin state."
    )
    @ApiResponse(responseCode = "200", description = "Explanation generated successfully")
    @ApiResponse(responseCode = "404", description = "Alert not found")
    public ResponseEntity<CopilotResponse> explainAlert(
            @Parameter(description = "Alert UUID") @PathVariable UUID alertId) {
        return ResponseEntity.ok(copilotService.explainAlert(alertId));
    }


    @GetMapping("/explain-recommendation/{recommendationId}")
    @Operation(summary = "Explain a recommendation", description = "Uses AI to generate a justification for a given recommendation.")
    public ResponseEntity<CopilotResponse> explainRecommendation(@PathVariable UUID recommendationId) {
        return ResponseEntity.ok(copilotService.explainRecommendation(recommendationId));
    }

    @PostMapping("/chat")
    @Operation(
            summary = "Chat with the Copilot",
            description = "Send a natural-language question to the AI Copilot. "
                    + "The copilot has access to the live Digital Twin state for the specified well."
    )
    @ApiResponse(responseCode = "200", description = "Chat response generated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request body")
    public ResponseEntity<CopilotResponse> chat(
            @Valid @RequestBody CopilotChatRequest request) {
        return ResponseEntity.ok(copilotService.chat(request.getWellId(), request.getMessage()));
    }
}
