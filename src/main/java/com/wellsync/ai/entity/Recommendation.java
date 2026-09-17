package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.RecommendationStatus;
import com.wellsync.ai.entity.enums.RecommendationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "recommendations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private RecommendationType recommendationType;

    @Column(precision = 12, scale = 4)
    private Double currentValue;

    @Column(precision = 12, scale = 4)
    private Double recommendedValue;

    @Column(length = 30)
    private String unit;

    @Column(precision = 5, scale = 2)
    private Double riskScore;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String factors;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private RecommendationStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant generatedAt;

    private Instant actedAt;
}
