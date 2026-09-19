package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.FailureSeverity;
import com.wellsync.ai.entity.enums.FailureType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FailureEventRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Failure type is required")
    private FailureType failureType;

    @NotNull(message = "Severity is required")
    private FailureSeverity severity;

    private Instant detectedAt;
    private Instant resolvedAt;
    private String description;
    private String rootCause;
    private Double relatedRiskScore;
}
