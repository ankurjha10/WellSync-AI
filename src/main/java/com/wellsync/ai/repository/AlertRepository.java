package com.wellsync.ai.repository;

import com.wellsync.ai.entity.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlertRepository extends JpaRepository<Alert, UUID> {
    List<Alert> findByWellIdOrderByCreatedAtDesc(UUID wellId);
    List<Alert> findByWellIdAndIsAcknowledgedFalse(UUID wellId);
}
