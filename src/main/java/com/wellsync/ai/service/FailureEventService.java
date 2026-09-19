package com.wellsync.ai.service;

import com.wellsync.ai.dto.FailureEventRequest;
import com.wellsync.ai.dto.FailureEventResponse;
import com.wellsync.ai.entity.FailureEvent;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.FailureEventMapper;
import com.wellsync.ai.repository.FailureEventRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FailureEventService {

    private final FailureEventRepository failureEventRepository;
    private final WellRepository wellRepository;
    private final FailureEventMapper failureEventMapper;

    @Transactional
    public FailureEventResponse create(FailureEventRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        FailureEvent event = failureEventMapper.toEntity(request);
        event.setWell(well);
        FailureEvent saved = failureEventRepository.saveAndFlush(event);
        return failureEventMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public FailureEventResponse getById(UUID id) {
        FailureEvent event = failureEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FailureEvent not found with id: " + id));
        return failureEventMapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    public List<FailureEventResponse> getAllByWellId(UUID wellId) {
        return failureEventRepository.findByWellIdOrderByDetectedAtDesc(wellId).stream()
                .map(failureEventMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FailureEventResponse update(UUID id, FailureEventRequest request) {
        FailureEvent event = failureEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FailureEvent not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        failureEventMapper.updateEntityFromRequest(request, event);
        event.setWell(well);
        FailureEvent updated = failureEventRepository.saveAndFlush(event);
        return failureEventMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!failureEventRepository.existsById(id)) {
            throw new ResourceNotFoundException("FailureEvent not found with id: " + id);
        }
        failureEventRepository.deleteById(id);
    }
}
