package com.wellsync.ai.repository;

import com.wellsync.ai.entity.WellTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WellTargetRepository extends JpaRepository<WellTarget, UUID> {
    Optional<WellTarget> findByWellId(UUID wellId);
}
