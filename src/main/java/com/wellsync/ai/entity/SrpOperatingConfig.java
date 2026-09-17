package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "srp_operating_configs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SrpOperatingConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "srp_system_id", nullable = false)
    private SrpSystem srpSystem;

    @Column(precision = 8, scale = 2)
    private Double strokeLength;

    @Column(precision = 6, scale = 2)
    private Double spm;

    @Column(precision = 6, scale = 2)
    private Double pumpRpm;

    @Column(precision = 8, scale = 2)
    private Double vfdFrequency;

    @Column(precision = 6, scale = 2)
    private Double targetRpm;

    @Column(precision = 5, scale = 2)
    private Double pumpEfficiencyPercent;

    @UpdateTimestamp
    private Instant updatedAt;
}
