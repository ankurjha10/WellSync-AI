package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.wellsync.ai.entity.enums.WellStatus;

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

    @Column(length = 100)
    private String pumpType;

    @Column(length = 100)
    private String pumpModel;

    @Column(length = 100)
    private String rodType;

    @Column(precision = 10)
    private Double rodStringLength;

    @Column(precision = 10)
    private Double pumpDepth;

    @Column(precision = 6)
    private Double minRpm;

    @Column(precision = 6)
    private Double maxRpm;

    @Column(precision = 8)
    private Double maxStrokeLength;

    @Column(precision = 12)
    private Double maxRodLoad;

    @Column(length = 30)
    @Enumerated(EnumType.STRING)
    private WellStatus status;

    @OneToOne(mappedBy = "srpSystem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private SrpOperatingConfig srpOperatingConfig;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
