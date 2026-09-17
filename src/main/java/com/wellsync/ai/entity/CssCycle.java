package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.CssCycleStatus;
import com.wellsync.ai.entity.enums.CssStage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "css_cycles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CssCycle {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    private Well well;

    private int cycleNumber;

    @Enumerated(EnumType.STRING)
    private CssStage stage;

    @Enumerated(EnumType.STRING)
    private CssCycleStatus status;

    private Instant startTime;

    private Instant endTime;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
