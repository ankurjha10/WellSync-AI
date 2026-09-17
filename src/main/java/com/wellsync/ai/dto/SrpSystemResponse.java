package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.WellStatus;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SrpSystemResponse {
    private UUID id;
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
    private Instant createdAt;
    private Instant updatedAt;
}
