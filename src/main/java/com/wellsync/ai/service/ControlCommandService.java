package com.wellsync.ai.service;

import com.wellsync.ai.dto.ControlCommandRequest;
import com.wellsync.ai.dto.ControlCommandResponse;
import com.wellsync.ai.entity.ControlCommand;
import com.wellsync.ai.entity.Recommendation;
import com.wellsync.ai.entity.User;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.ControlCommandMapper;
import com.wellsync.ai.repository.ControlCommandRepository;
import com.wellsync.ai.repository.RecommendationRepository;
import com.wellsync.ai.repository.UserRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ControlCommandService {

    private final ControlCommandRepository controlCommandRepository;
    private final WellRepository wellRepository;
    private final RecommendationRepository recommendationRepository;
    private final UserRepository userRepository;
    private final ControlCommandMapper controlCommandMapper;

    @Transactional
    public ControlCommandResponse create(ControlCommandRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        ControlCommand command = controlCommandMapper.toEntity(request);
        command.setWell(well);

        if (request.getRecommendationId() != null) {
            Recommendation recommendation = recommendationRepository.findById(request.getRecommendationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + request.getRecommendationId()));
            command.setRecommendation(recommendation);
        }

        if (request.getRequestedById() != null) {
            User user = userRepository.findById(request.getRequestedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getRequestedById()));
            command.setRequestedBy(user);
        }

        ControlCommand saved = controlCommandRepository.saveAndFlush(command);
        return controlCommandMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ControlCommandResponse getById(UUID id) {
        ControlCommand command = controlCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ControlCommand not found with id: " + id));
        return controlCommandMapper.toResponse(command);
    }

    @Transactional(readOnly = true)
    public List<ControlCommandResponse> getAllByWellId(UUID wellId) {
        return controlCommandRepository.findByWellIdOrderByRequestedAtDesc(wellId).stream()
                .map(controlCommandMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ControlCommandResponse update(UUID id, ControlCommandRequest request) {
        ControlCommand command = controlCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ControlCommand not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        controlCommandMapper.updateEntityFromRequest(request, command);
        command.setWell(well);

        if (request.getRecommendationId() != null) {
            Recommendation recommendation = recommendationRepository.findById(request.getRecommendationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + request.getRecommendationId()));
            command.setRecommendation(recommendation);
        } else {
            command.setRecommendation(null);
        }

        if (request.getRequestedById() != null) {
            User user = userRepository.findById(request.getRequestedById())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + request.getRequestedById()));
            command.setRequestedBy(user);
        } else {
            command.setRequestedBy(null);
        }

        ControlCommand updated = controlCommandRepository.saveAndFlush(command);
        return controlCommandMapper.toResponse(updated);
    }

    @Transactional
    public ControlCommandResponse markExecuted(UUID id) {
        ControlCommand command = controlCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ControlCommand not found with id: " + id));
        command.setExecutedAt(Instant.now());
        ControlCommand updated = controlCommandRepository.saveAndFlush(command);
        return controlCommandMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!controlCommandRepository.existsById(id)) {
            throw new ResourceNotFoundException("ControlCommand not found with id: " + id);
        }
        controlCommandRepository.deleteById(id);
    }
}
