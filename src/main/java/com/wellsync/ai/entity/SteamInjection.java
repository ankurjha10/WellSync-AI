package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "steam_injections")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SteamInjection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "css_cycle_id", nullable = false)
    private CssCycle cssCycle;

    @Column(precision = 12)
    private Double steamRateKgHr;

    @Column(precision = 15)
    private Double steamVolumeKg;

    @Column(precision = 8)
    private Double steamTemperatureC;

    @Column(precision = 10)
    private Double injectionPressurePsi;

    private Integer injectionDurationMinutes;

    @Column(precision = 5)
    private Double steamQualityPercent;

    private Instant startTime;

    private Instant endTime;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
