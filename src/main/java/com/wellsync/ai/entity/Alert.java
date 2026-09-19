package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.AlertSeverity;
import com.wellsync.ai.entity.enums.AlertType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "alerts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private AlertType alertType;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AlertSeverity severity;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(precision = 5)
    private Double riskScore;

    @Builder.Default
    private boolean isAcknowledged = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acknowledged_by")
    private User acknowledgedBy;

    private Instant acknowledgedAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
