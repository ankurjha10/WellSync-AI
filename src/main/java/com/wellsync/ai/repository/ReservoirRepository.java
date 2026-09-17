package com.wellsync.ai.repository;

import com.wellsync.ai.entity.Reservoir;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservoirRepository extends JpaRepository<Reservoir, UUID> {
}
