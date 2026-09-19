package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.FailureSeverity;
import com.wellsync.ai.entity.enums.FailureType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "failure_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FailureEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private FailureType failureType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private FailureSeverity severity;

    private Instant detectedAt;

    private Instant resolvedAt;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String rootCause;

    @Column(precision = 5)
    private Double relatedRiskScore;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
