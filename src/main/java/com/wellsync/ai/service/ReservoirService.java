package com.wellsync.ai.service;

import com.wellsync.ai.dto.ReservoirRequest;
import com.wellsync.ai.dto.ReservoirResponse;
import com.wellsync.ai.entity.Reservoir;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.ReservoirMapper;
import com.wellsync.ai.repository.ReservoirRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservoirService {

    private final ReservoirRepository reservoirRepository;
    private final ReservoirMapper reservoirMapper;

    @Transactional
    public ReservoirResponse create(ReservoirRequest request) {
        Reservoir reservoir = reservoirMapper.toEntity(request);
        Reservoir saved = reservoirRepository.saveAndFlush(reservoir);
        return reservoirMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ReservoirResponse getById(UUID id) {
        Reservoir reservoir = reservoirRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservoir not found with id: " + id));
        return reservoirMapper.toResponse(reservoir);
    }

    @Transactional(readOnly = true)
    public List<ReservoirResponse> getAll() {
        return reservoirRepository.findAll().stream()
                .map(reservoirMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReservoirResponse update(UUID id, ReservoirRequest request) {
        Reservoir reservoir = reservoirRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservoir not found with id: " + id));
        reservoirMapper.updateEntityFromRequest(request, reservoir);
        Reservoir updated = reservoirRepository.saveAndFlush(reservoir);
        return reservoirMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!reservoirRepository.existsById(id)) {
            throw new ResourceNotFoundException("Reservoir not found with id: " + id);
        }
        reservoirRepository.deleteById(id);
    }
}
