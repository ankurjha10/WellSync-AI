package com.wellsync.ai.repository;

import com.wellsync.ai.entity.SrpOperatingConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SrpOperatingConfigRepository extends JpaRepository<SrpOperatingConfig, UUID> {
    Optional<SrpOperatingConfig> findBySrpSystemId(UUID srpSystemId);
}
