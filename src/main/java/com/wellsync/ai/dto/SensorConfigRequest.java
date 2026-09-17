package com.wellsync.ai.dto;

import com.wellsync.ai.entity.enums.SensorType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class SensorConfigRequest {
    @NotNull(message = "Well ID is required")
    private UUID wellId;

    @NotBlank(message = "Sensor code is required")
    private String sensorCode;

    @NotNull(message = "Sensor type is required")
    private SensorType sensorType;

    private String unit;
    private Double minValue;
    private Double maxValue;
    private Integer samplingIntervalSeconds;
    private Boolean isActive;
}
