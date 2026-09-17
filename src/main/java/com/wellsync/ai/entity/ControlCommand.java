package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.CommandSource;
import com.wellsync.ai.entity.enums.CommandStatus;
import com.wellsync.ai.entity.enums.CommandType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "control_commands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ControlCommand {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false)
    private Well well;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private CommandType commandType;

    @Column(precision = 12, scale = 4)
    private Double previousValue;

    @Column(precision = 12, scale = 4)
    private Double requestedValue;

    @Column(length = 30)
    private String unit;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CommandSource source;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CommandStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by")
    private User requestedBy;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(columnDefinition = "TEXT")
    private String failureReason;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant requestedAt;

    private Instant executedAt;
}
