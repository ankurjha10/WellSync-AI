package com.wellsync.ai.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reservoirs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservoir {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 150)
    private String formation;

    @Column(length = 100)
    private String lithology;

    @Column(precision = 5)
    private Double apiGravity;

    @Column(precision = 8)
    private Double initialTemperatureC;

    @Column(precision = 10)
    private Double initialPressurePsi;

    @Column(precision = 12)
    private Double oilViscosityCp;

    @Column(precision = 5)
    private Double porosityPercent;

    @Column(precision = 12)
    private Double permeabilityMd;

    @Column(precision = 10)
    private Double depthM;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
