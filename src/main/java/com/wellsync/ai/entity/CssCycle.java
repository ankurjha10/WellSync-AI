package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.CssCycleStatus;
import com.wellsync.ai.entity.enums.CssStage;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
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

    @Column(nullable = false)
    private int cycleNumber;

    @Enumerated(EnumType.STRING)
    @Column(length = 40)
    private CssStage stage;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private CssCycleStatus status;

    private Integer soakTimeMinutes;

    private Instant startTime;

    private Instant endTime;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @OneToMany(mappedBy = "cssCycle", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SteamInjection> steamInjections;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
