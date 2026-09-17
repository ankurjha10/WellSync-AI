package com.wellsync.ai.repository;

import com.wellsync.ai.entity.SteamInjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SteamInjectionRepository extends JpaRepository<SteamInjection, UUID> {
    List<SteamInjection> findByCssCycleId(UUID cssCycleId);
}
