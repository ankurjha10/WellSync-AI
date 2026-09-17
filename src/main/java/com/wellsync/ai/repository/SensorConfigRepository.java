package com.wellsync.ai.repository;

import com.wellsync.ai.entity.SensorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SensorConfigRepository extends JpaRepository<SensorConfig, UUID> {
    List<SensorConfig> findByWellId(UUID wellId);
}
