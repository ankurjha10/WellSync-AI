package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.SensorType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "sensor_configs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"well_id", "sensor_code"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @Column(nullable = false, length = 100)
    private String sensorCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private SensorType sensorType;

    @Column(length = 30)
    private String unit;

    @Column(precision = 15)
    private Double minValue;

    @Column(precision = 15)
    private Double maxValue;

    @Builder.Default
    private int samplingIntervalSeconds = 1;

    @Builder.Default
    private boolean isActive = true;

    private Instant lastSeenAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}