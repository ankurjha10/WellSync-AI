package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.WellStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SrpSystemRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    private String pumpType;
    private String pumpModel;
    private String rodType;
    private Double rodStringLength;
    private Double pumpDepth;
    private Double minRpm;
    private Double maxRpm;
    private Double maxStrokeLength;
    private Double maxRodLoad;
    private WellStatus status;
}
