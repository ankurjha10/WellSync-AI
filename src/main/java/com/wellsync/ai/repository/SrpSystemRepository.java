package com.wellsync.ai.repository;

import com.wellsync.ai.entity.SrpSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SrpSystemRepository extends JpaRepository<SrpSystem, UUID> {
    Optional<SrpSystem> findByWellId(UUID wellId);
}
