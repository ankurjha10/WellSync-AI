package com.wellsync.ai.entity;

import com.wellsync.ai.entity.enums.SystemEventType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "system_events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SystemEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "well_id")
    private Well well;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private SystemEventType eventType;

    @Column(length = 20)
    private String severity;

    @Column(columnDefinition = "TEXT")
    private String message;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String metadata;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
