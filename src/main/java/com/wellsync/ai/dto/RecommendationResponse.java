package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.RecommendationStatus;
import com.wellsync.ai.entity.enums.RecommendationType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class RecommendationResponse {
    private UUID id;
    private UUID wellId;
    private RecommendationType recommendationType;
    private Double currentValue;
    private Double recommendedValue;
    private String unit;
    private Double riskScore;
    private String reason;
    private String factors;
    private RecommendationStatus status;
    private Instant generatedAt;
    private Instant actedAt;
}
