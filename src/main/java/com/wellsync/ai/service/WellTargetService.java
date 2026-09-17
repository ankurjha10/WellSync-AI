package com.wellsync.ai.service;

import com.wellsync.ai.dto.WellTargetRequest;
import com.wellsync.ai.dto.WellTargetResponse;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.entity.WellTarget;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.WellTargetMapper;
import com.wellsync.ai.repository.WellRepository;
import com.wellsync.ai.repository.WellTargetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellTargetService {

    private final WellTargetRepository wellTargetRepository;
    private final WellRepository wellRepository;
    private final WellTargetMapper wellTargetMapper;

    @Transactional
    public WellTargetResponse create(WellTargetRequest request) {
        if (wellTargetRepository.findByWellId(request.getWellId()).isPresent()) {
            throw new ConflictException("WellTarget already exists for wellId: " + request.getWellId());
        }
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        WellTarget target = wellTargetMapper.toEntity(request);
        target.setWell(well);
        WellTarget saved = wellTargetRepository.save(target);
        return wellTargetMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public WellTargetResponse getById(UUID id) {
        WellTarget target = wellTargetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WellTarget not found with id: " + id));
        return wellTargetMapper.toResponse(target);
    }

    @Transactional(readOnly = true)
    public List<WellTargetResponse> getAll() {
        return wellTargetRepository.findAll().stream()
                .map(wellTargetMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public WellTargetResponse update(UUID id, WellTargetRequest request) {
        WellTarget target = wellTargetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WellTarget not found with id: " + id));

        if (!target.getWell().getId().equals(request.getWellId())) {
            if (wellTargetRepository.findByWellId(request.getWellId()).isPresent()) {
                throw new ConflictException("WellTarget already exists for wellId: " + request.getWellId());
            }
        }

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        wellTargetMapper.updateEntityFromRequest(request, target);
        target.setWell(well);
        WellTarget updated = wellTargetRepository.save(target);
        return wellTargetMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!wellTargetRepository.existsById(id)) {
            throw new ResourceNotFoundException("WellTarget not found with id: " + id);
        }
        wellTargetRepository.deleteById(id);
    }
}
