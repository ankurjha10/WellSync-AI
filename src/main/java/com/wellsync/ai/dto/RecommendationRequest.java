package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.RecommendationStatus;
import com.wellsync.ai.entity.enums.RecommendationType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RecommendationRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotNull(message = "Recommendation type is required")
    private RecommendationType recommendationType;

    private Double currentValue;
    private Double recommendedValue;
    private String unit;
    private Double riskScore;
    private String reason;
    private String factors;

    @NotNull(message = "Status is required")
    private RecommendationStatus status;
}
