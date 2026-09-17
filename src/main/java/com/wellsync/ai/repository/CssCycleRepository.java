package com.wellsync.ai.repository;

import com.wellsync.ai.entity.CssCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CssCycleRepository extends JpaRepository<CssCycle, UUID> {
    List<CssCycle> findByWellIdOrderByCycleNumberDesc(UUID wellId);
}
