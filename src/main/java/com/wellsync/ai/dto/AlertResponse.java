package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.AlertSeverity;
import com.wellsync.ai.entity.enums.AlertType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class AlertResponse {
    private UUID id;
    private UUID wellId;
    private AlertType alertType;
    private AlertSeverity severity;
    private String title;
    private String message;
    private Double riskScore;
    private boolean isAcknowledged;
    private UUID acknowledgedById;
    private Instant acknowledgedAt;
    private Instant createdAt;
}
