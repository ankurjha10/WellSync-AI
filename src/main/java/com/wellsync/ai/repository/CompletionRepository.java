package com.wellsync.ai.repository;

import com.wellsync.ai.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompletionRepository extends JpaRepository<Completion, UUID> {
    Optional<Completion> findByWellId(UUID wellId);
}
