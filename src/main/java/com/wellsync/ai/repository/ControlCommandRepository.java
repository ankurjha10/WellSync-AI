package com.wellsync.ai.repository;

import com.wellsync.ai.entity.ControlCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ControlCommandRepository extends JpaRepository<ControlCommand, UUID> {
    List<ControlCommand> findByWellIdOrderByRequestedAtDesc(UUID wellId);
}
