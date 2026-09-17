package com.wellsync.ai.repository;

import com.wellsync.ai.entity.ProductionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductionRecordRepository extends JpaRepository<ProductionRecord, UUID> {
    List<ProductionRecord> findByWellIdOrderByRecordedAtDesc(UUID wellId);
}
