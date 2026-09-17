package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "srp_systems")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SrpSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false, updatable = false)
    private Well well;

    private String pumpType;

    private String pumpModel;

    private String rodType;

    @Column(precision = 10, scale = 2)
    private Double rodStringLength;

    @Column(precision = 10, scale = 2)
    private Double pumpDepth;

    @Column(precision = 6, scale = 2)
    private Double minRpm;

    @Column(precision = 6, scale = 2)
    private Double maxRpm;

    @Column(precision = 8, scale = 2)
    private Double maxStrokeLength;

    @Column(precision = 12, scale = 2)
    private Double maxRodLoad;

    private String status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
