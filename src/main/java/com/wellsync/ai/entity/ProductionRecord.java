package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "production_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @Column(nullable = false)
    private Instant recordedAt;

    @Column(precision = 10)
    private Double oilRateBopd;

    @Column(precision = 10)
    private Double waterRateBwpd;

    @Column(precision = 10)
    private Double gasRateMscfd;

    @Column(precision = 5)
    private Double waterCutPercent;

    @Column(precision = 10)
    private Double steamOilRatio;

    @Column(precision = 12)
    private Double energyConsumptionKwh;

    @Column(precision = 5)
    private Double pumpEfficiencyPercent;
}
