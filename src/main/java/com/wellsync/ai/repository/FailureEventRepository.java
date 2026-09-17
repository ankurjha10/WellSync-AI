package com.wellsync.ai.repository;

import com.wellsync.ai.entity.FailureEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FailureEventRepository extends JpaRepository<FailureEvent, UUID> {
    List<FailureEvent> findByWellIdOrderByDetectedAtDesc(UUID wellId);
}
