package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.FailureSeverity;
import com.wellsync.ai.entity.enums.FailureType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class FailureEventResponse {
    private UUID id;
    private UUID wellId;
    private FailureType failureType;
    private FailureSeverity severity;
    private Instant detectedAt;
    private Instant resolvedAt;
    private String description;
    private String rootCause;
    private Double relatedRiskScore;
    private Instant createdAt;
}
