package com.wellsync.ai.repository;

import com.wellsync.ai.entity.SystemEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SystemEventRepository extends JpaRepository<SystemEvent, UUID> {
    List<SystemEvent> findByWellIdOrderByCreatedAtDesc(UUID wellId);
}
