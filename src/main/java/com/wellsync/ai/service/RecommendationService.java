package com.wellsync.ai.service;

import com.wellsync.ai.dto.RecommendationRequest;
import com.wellsync.ai.dto.RecommendationResponse;
import com.wellsync.ai.entity.Recommendation;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.RecommendationMapper;
import com.wellsync.ai.repository.RecommendationRepository;
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
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;
    private final WellRepository wellRepository;
    private final RecommendationMapper recommendationMapper;

    @Transactional
    public RecommendationResponse create(RecommendationRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        Recommendation recommendation = recommendationMapper.toEntity(request);
        recommendation.setWell(well);
        Recommendation saved = recommendationRepository.saveAndFlush(recommendation);
        return recommendationMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public RecommendationResponse getById(UUID id) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));
        return recommendationMapper.toResponse(recommendation);
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponse> getAllByWellId(UUID wellId) {
        return recommendationRepository.findByWellIdOrderByGeneratedAtDesc(wellId).stream()
                .map(recommendationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public RecommendationResponse update(UUID id, RecommendationRequest request) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        recommendationMapper.updateEntityFromRequest(request, recommendation);
        recommendation.setWell(well);
        Recommendation updated = recommendationRepository.saveAndFlush(recommendation);
        return recommendationMapper.toResponse(updated);
    }

    @Transactional
    public RecommendationResponse markActed(UUID id) {
        Recommendation recommendation = recommendationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + id));
        recommendation.setActedAt(Instant.now());
        Recommendation updated = recommendationRepository.saveAndFlush(recommendation);
        return recommendationMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!recommendationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recommendation not found with id: " + id);
        }
        recommendationRepository.deleteById(id);
    }
}
