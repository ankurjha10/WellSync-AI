package com.wellsync.ai.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "completions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id", nullable = false, unique = true)
    private Well well;

    @Column(length = 50)
    private String completionType;

    @Column(precision = 10, scale = 2)
    private Double tubingDepth;

    @Column(precision = 10, scale = 2)
    private Double casingDepth;

    @Column(precision = 10, scale = 2)
    private Double perforationTop;

    @Column(precision = 10, scale = 2)
    private Double perforationBottom;

    @Column(precision = 10, scale = 2)
    private Double pumpSettingDepth;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

}
