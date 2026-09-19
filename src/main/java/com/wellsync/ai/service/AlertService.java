package com.wellsync.ai.service;

import com.wellsync.ai.dto.AlertRequest;
import com.wellsync.ai.dto.AlertResponse;
import com.wellsync.ai.entity.Alert;
import com.wellsync.ai.entity.User;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.AlertMapper;
import com.wellsync.ai.repository.AlertRepository;
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
public class AlertService {

    private final AlertRepository alertRepository;
    private final WellRepository wellRepository;
    private final UserRepository userRepository;
    private final AlertMapper alertMapper;

    @Transactional
    public AlertResponse create(AlertRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        Alert alert = alertMapper.toEntity(request);
        alert.setWell(well);
        Alert saved = alertRepository.saveAndFlush(alert);
        return alertMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public AlertResponse getById(UUID id) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));
        return alertMapper.toResponse(alert);
    }

    @Transactional(readOnly = true)
    public List<AlertResponse> getAllByWellId(UUID wellId) {
        return alertRepository.findByWellIdOrderByCreatedAtDesc(wellId).stream()
                .map(alertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AlertResponse> getUnacknowledgedByWellId(UUID wellId) {
        return alertRepository.findByWellIdAndIsAcknowledgedFalse(wellId).stream()
                .map(alertMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AlertResponse acknowledge(UUID alertId, UUID userId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + alertId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        alert.setAcknowledged(true);
        alert.setAcknowledgedBy(user);
        alert.setAcknowledgedAt(Instant.now());

        Alert updated = alertRepository.saveAndFlush(alert);
        return alertMapper.toResponse(updated);
    }

    @Transactional
    public AlertResponse update(UUID id, AlertRequest request) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        alertMapper.updateEntityFromRequest(request, alert);
        alert.setWell(well);
        Alert updated = alertRepository.saveAndFlush(alert);
        return alertMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!alertRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alert not found with id: " + id);
        }
        alertRepository.deleteById(id);
    }
}
