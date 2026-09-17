package com.wellsync.ai.repository;

import com.wellsync.ai.entity.Well;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface WellRepository extends JpaRepository<Well, UUID> {

    Optional<Well> findByWellCode(String wellCode);
}
