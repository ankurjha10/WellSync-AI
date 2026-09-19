package com.wellsync.ai.service;

import com.wellsync.ai.dto.SystemEventRequest;
import com.wellsync.ai.dto.SystemEventResponse;
import com.wellsync.ai.entity.SystemEvent;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.SystemEventMapper;
import com.wellsync.ai.repository.SystemEventRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SystemEventService {

    private final SystemEventRepository systemEventRepository;
    private final WellRepository wellRepository;
    private final SystemEventMapper systemEventMapper;

    @Transactional
    public SystemEventResponse create(SystemEventRequest request) {
        SystemEvent event = systemEventMapper.toEntity(request);

        if (request.getWellId() != null) {
            Well well = wellRepository.findById(request.getWellId())
                    .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));
            event.setWell(well);
        }

        SystemEvent saved = systemEventRepository.saveAndFlush(event);
        return systemEventMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SystemEventResponse getById(UUID id) {
        SystemEvent event = systemEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SystemEvent not found with id: " + id));
        return systemEventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public List<SystemEventResponse> getAllByWellId(UUID wellId) {
        return systemEventRepository.findByWellIdOrderByCreatedAtDesc(wellId).stream()
                .map(systemEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SystemEventResponse> getAll() {
        return systemEventRepository.findAll().stream()
                .map(systemEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(UUID id) {
        if (!systemEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("SystemEvent not found with id: " + id);
        }
        systemEventRepository.deleteById(id);
    }
}
