package com.wellsync.ai.service;

import com.wellsync.ai.dto.SrpSystemRequest;
import com.wellsync.ai.dto.SrpSystemResponse;
import com.wellsync.ai.entity.SrpSystem;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.SrpSystemMapper;
import com.wellsync.ai.repository.SrpSystemRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SrpSystemService {

    private final SrpSystemRepository srpSystemRepository;
    private final WellRepository wellRepository;
    private final SrpSystemMapper srpSystemMapper;

    @Transactional
    public SrpSystemResponse create(SrpSystemRequest request) {
        if (srpSystemRepository.findByWellId(request.getWellId()).isPresent()) {
            throw new ConflictException("SrpSystem already exists for wellId: " + request.getWellId());
        }
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        SrpSystem srpSystem = srpSystemMapper.toEntity(request);
        srpSystem.setWell(well);
        SrpSystem saved = srpSystemRepository.saveAndFlush(srpSystem);
        return srpSystemMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SrpSystemResponse getById(UUID id) {
        SrpSystem srpSystem = srpSystemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SrpSystem not found with id: " + id));
        return srpSystemMapper.toResponse(srpSystem);
    }

    @Transactional(readOnly = true)
    public List<SrpSystemResponse> getAll() {
        return srpSystemRepository.findAll().stream()
                .map(srpSystemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SrpSystemResponse update(UUID id, SrpSystemRequest request) {
        SrpSystem srpSystem = srpSystemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SrpSystem not found with id: " + id));

        if (!srpSystem.getWell().getId().equals(request.getWellId())) {
            if (srpSystemRepository.findByWellId(request.getWellId()).isPresent()) {
                throw new ConflictException("SrpSystem already exists for wellId: " + request.getWellId());
            }
        }

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        srpSystemMapper.updateEntityFromRequest(request, srpSystem);
        srpSystem.setWell(well);
        SrpSystem updated = srpSystemRepository.saveAndFlush(srpSystem);
        return srpSystemMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!srpSystemRepository.existsById(id)) {
            throw new ResourceNotFoundException("SrpSystem not found with id: " + id);
        }
        srpSystemRepository.deleteById(id);
    }
}
