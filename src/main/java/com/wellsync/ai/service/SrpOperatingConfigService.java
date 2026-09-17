package com.wellsync.ai.service;

import com.wellsync.ai.dto.SrpOperatingConfigRequest;
import com.wellsync.ai.dto.SrpOperatingConfigResponse;
import com.wellsync.ai.entity.SrpOperatingConfig;
import com.wellsync.ai.entity.SrpSystem;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.SrpOperatingConfigMapper;
import com.wellsync.ai.repository.SrpOperatingConfigRepository;
import com.wellsync.ai.repository.SrpSystemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SrpOperatingConfigService {

    private final SrpOperatingConfigRepository srpOperatingConfigRepository;
    private final SrpSystemRepository srpSystemRepository;
    private final SrpOperatingConfigMapper srpOperatingConfigMapper;

    @Transactional
    public SrpOperatingConfigResponse create(SrpOperatingConfigRequest request) {
        if (srpOperatingConfigRepository.findBySrpSystemId(request.getSrpSystemId()).isPresent()) {
            throw new ConflictException("SrpOperatingConfig already exists for srpSystemId: " + request.getSrpSystemId());
        }
        SrpSystem srpSystem = srpSystemRepository.findById(request.getSrpSystemId())
                .orElseThrow(() -> new ResourceNotFoundException("SrpSystem not found with id: " + request.getSrpSystemId()));

        SrpOperatingConfig config = srpOperatingConfigMapper.toEntity(request);
        config.setSrpSystem(srpSystem);
        SrpOperatingConfig saved = srpOperatingConfigRepository.save(config);
        return srpOperatingConfigMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SrpOperatingConfigResponse getById(UUID id) {
        SrpOperatingConfig config = srpOperatingConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SrpOperatingConfig not found with id: " + id));
        return srpOperatingConfigMapper.toResponse(config);
    }

    @Transactional(readOnly = true)
    public List<SrpOperatingConfigResponse> getAll() {
        return srpOperatingConfigRepository.findAll().stream()
                .map(srpOperatingConfigMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SrpOperatingConfigResponse update(UUID id, SrpOperatingConfigRequest request) {
        SrpOperatingConfig config = srpOperatingConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SrpOperatingConfig not found with id: " + id));

        if (!config.getSrpSystem().getId().equals(request.getSrpSystemId())) {
            if (srpOperatingConfigRepository.findBySrpSystemId(request.getSrpSystemId()).isPresent()) {
                throw new ConflictException("SrpOperatingConfig already exists for srpSystemId: " + request.getSrpSystemId());
            }
        }

        SrpSystem srpSystem = srpSystemRepository.findById(request.getSrpSystemId())
                .orElseThrow(() -> new ResourceNotFoundException("SrpSystem not found with id: " + request.getSrpSystemId()));

        srpOperatingConfigMapper.updateEntityFromRequest(request, config);
        config.setSrpSystem(srpSystem);
        SrpOperatingConfig updated = srpOperatingConfigRepository.save(config);
        return srpOperatingConfigMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!srpOperatingConfigRepository.existsById(id)) {
            throw new ResourceNotFoundException("SrpOperatingConfig not found with id: " + id);
        }
        srpOperatingConfigRepository.deleteById(id);
    }
}
