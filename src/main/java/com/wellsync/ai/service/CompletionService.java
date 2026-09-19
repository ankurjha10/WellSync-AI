package com.wellsync.ai.service;

import com.wellsync.ai.dto.CompletionRequest;
import com.wellsync.ai.dto.CompletionResponse;
import com.wellsync.ai.entity.Completion;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ConflictException;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.CompletionMapper;
import com.wellsync.ai.repository.CompletionRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompletionService {

    private final CompletionRepository completionRepository;
    private final WellRepository wellRepository;
    private final CompletionMapper completionMapper;

    @Transactional
    public CompletionResponse create(CompletionRequest request) {
        if (completionRepository.findByWellId(request.getWellId()).isPresent()) {
            throw new ConflictException("Completion already exists for wellId: " + request.getWellId());
        }
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        Completion completion = completionMapper.toEntity(request);
        completion.setWell(well);
        Completion saved = completionRepository.saveAndFlush(completion);
        return completionMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CompletionResponse getById(UUID id) {
        Completion completion = completionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Completion not found with id: " + id));
        return completionMapper.toResponse(completion);
    }

    @Transactional(readOnly = true)
    public List<CompletionResponse> getAll() {
        return completionRepository.findAll().stream()
                .map(completionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CompletionResponse update(UUID id, CompletionRequest request) {
        Completion completion = completionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Completion not found with id: " + id));

        if (!completion.getWell().getId().equals(request.getWellId())) {
            if (completionRepository.findByWellId(request.getWellId()).isPresent()) {
                throw new ConflictException("Completion already exists for wellId: " + request.getWellId());
            }
        }

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        completionMapper.updateEntityFromRequest(request, completion);
        completion.setWell(well);
        Completion updated = completionRepository.saveAndFlush(completion);
        return completionMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!completionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Completion not found with id: " + id);
        }
        completionRepository.deleteById(id);
    }
}
