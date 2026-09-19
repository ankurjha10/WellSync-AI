package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.AlertSeverity;
import com.wellsync.ai.entity.enums.AlertType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AlertRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Alert type is required")
    private AlertType alertType;

    @NotNull(message = "Severity is required")
    private AlertSeverity severity;

    private String title;
    private String message;
    private Double riskScore;
}
