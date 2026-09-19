package com.wellsync.ai.service;

import com.wellsync.ai.dto.WellRequest;
import com.wellsync.ai.dto.WellResponse;
import com.wellsync.ai.entity.Reservoir;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.WellMapper;
import com.wellsync.ai.repository.ReservoirRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellService {

    private final WellRepository wellRepository;
    private final ReservoirRepository reservoirRepository;
    private final WellMapper wellMapper;

    @Transactional
    public WellResponse create(WellRequest request) {
        if (wellRepository.findByWellCode(request.getWellCode()).isPresent()) {
            throw new ConflictException("Well already exists with code: " + request.getWellCode());
        }
        Reservoir reservoir = reservoirRepository.findById(request.getReservoirId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservoir not found with id: " + request.getReservoirId()));

        Well well = wellMapper.toEntity(request);
        well.setReservoir(reservoir);
        Well saved = wellRepository.saveAndFlush(well);
        return wellMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WellResponse getById(UUID id) {
        Well well = wellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + id));
        return wellMapper.toResponse(well);
    }

    @Transactional(readOnly = true)
    public List<WellResponse> getAll() {
        return wellRepository.findAll().stream()
                .map(wellMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public WellResponse update(UUID id, WellRequest request) {
        Well well = wellRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + id));

        if (!well.getWellCode().equals(request.getWellCode()) && 
            wellRepository.findByWellCode(request.getWellCode()).isPresent()) {
            throw new ConflictException("Well already exists with code: " + request.getWellCode());
        }

        Reservoir reservoir = reservoirRepository.findById(request.getReservoirId())
                .orElseThrow(() -> new ResourceNotFoundException("Reservoir not found with id: " + request.getReservoirId()));

        wellMapper.updateEntityFromRequest(request, well);
        well.setReservoir(reservoir);
        Well updated = wellRepository.saveAndFlush(well);
        return wellMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!wellRepository.existsById(id)) {
            throw new ResourceNotFoundException("Well not found with id: " + id);
        }
        wellRepository.deleteById(id);
    }
}
