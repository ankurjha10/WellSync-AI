package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "well_targets")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WellTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false, unique = true)
    private Well well;

    @Column(precision = 6)
    private Double minRpm;

    @Column(precision = 6)
    private Double maxRpm;

    @Column(precision = 6)
    private Double targetRpm;

    @Column(precision = 8)
    private Double minTemperatureC;

    @Column(precision = 12)
    private Double maxViscosityCp;

    @Column(precision = 12)
    private Double maxRodLoadLbs;

    @Column(precision = 10)
    private Double targetOilRateBopd;

    @Column(precision = 12)
    private Double maxEnergyKwh;

    @UpdateTimestamp
    private Instant updatedAt;
}
