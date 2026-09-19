package com.wellsync.ai.service;

import com.wellsync.ai.dto.SensorConfigRequest;
import com.wellsync.ai.dto.SensorConfigResponse;
import com.wellsync.ai.entity.SensorConfig;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.SensorConfigMapper;
import com.wellsync.ai.repository.SensorConfigRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorConfigService {

    private final SensorConfigRepository sensorConfigRepository;
    private final WellRepository wellRepository;
    private final SensorConfigMapper sensorConfigMapper;

    @Transactional
    public SensorConfigResponse create(SensorConfigRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        SensorConfig config = sensorConfigMapper.toEntity(request);
        config.setWell(well);
        if (request.getIsActive() != null) {
            config.setActive(request.getIsActive());
        }
        if (request.getSamplingIntervalSeconds() != null) {
            config.setSamplingIntervalSeconds(request.getSamplingIntervalSeconds());
        }
        
        SensorConfig saved = sensorConfigRepository.saveAndFlush(config);
        return sensorConfigMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SensorConfigResponse getById(UUID id) {
        SensorConfig config = sensorConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SensorConfig not found with id: " + id));
        return sensorConfigMapper.toResponse(config);
    }

    @Transactional(readOnly = true)
    public List<SensorConfigResponse> getAllByWellId(UUID wellId) {
        return sensorConfigRepository.findByWellId(wellId).stream()
                .map(sensorConfigMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SensorConfigResponse update(UUID id, SensorConfigRequest request) {
        SensorConfig config = sensorConfigRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SensorConfig not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        sensorConfigMapper.updateEntityFromRequest(request, config);
        config.setWell(well);
        if (request.getIsActive() != null) {
            config.setActive(request.getIsActive());
        }
        if (request.getSamplingIntervalSeconds() != null) {
            config.setSamplingIntervalSeconds(request.getSamplingIntervalSeconds());
        }
        
        SensorConfig updated = sensorConfigRepository.saveAndFlush(config);
        return sensorConfigMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!sensorConfigRepository.existsById(id)) {
            throw new ResourceNotFoundException("SensorConfig not found with id: " + id);
        }
        sensorConfigRepository.deleteById(id);
    }
}
