package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.SensorType;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SensorConfigResponse {
    private UUID id;
    private UUID wellId;
    private String sensorCode;
    private SensorType sensorType;
    private String unit;
    private Double minValue;
    private Double maxValue;
    private Integer samplingIntervalSeconds;
    private boolean isActive;
    private Instant lastSeenAt;
    private Instant createdAt;
}
