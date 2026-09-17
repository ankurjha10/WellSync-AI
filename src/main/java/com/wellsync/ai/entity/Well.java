package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.WellStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "wells")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Well {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String wellCode;

    @Column(length = 150)
    private String wellName;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String fieldName;

    @Column(length = 255)
    private String location;

    @Column(precision = 5, scale = 2)
    private Double apiGravity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "reservoir_id", nullable = false)
    private Reservoir reservoir;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private WellStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
