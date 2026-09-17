package com.wellsync.ai.service;

import com.wellsync.ai.dto.SteamInjectionRequest;
import com.wellsync.ai.dto.SteamInjectionResponse;
import com.wellsync.ai.entity.CssCycle;
import com.wellsync.ai.entity.SteamInjection;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.SteamInjectionMapper;
import com.wellsync.ai.repository.CssCycleRepository;
import com.wellsync.ai.repository.SteamInjectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SteamInjectionService {

    private final SteamInjectionRepository steamInjectionRepository;
    private final CssCycleRepository cssCycleRepository;
    private final SteamInjectionMapper steamInjectionMapper;

    @Transactional
    public SteamInjectionResponse create(SteamInjectionRequest request) {
        CssCycle cycle = cssCycleRepository.findById(request.getCssCycleId())
                .orElseThrow(() -> new ResourceNotFoundException("CssCycle not found with id: " + request.getCssCycleId()));

        SteamInjection injection = steamInjectionMapper.toEntity(request);
        injection.setCssCycle(cycle);
        SteamInjection saved = steamInjectionRepository.save(injection);
        return steamInjectionMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public SteamInjectionResponse getById(UUID id) {
        SteamInjection injection = steamInjectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SteamInjection not found with id: " + id));
        return steamInjectionMapper.toResponse(injection);
    }

    @Transactional(readOnly = true)
    public List<SteamInjectionResponse> getAllByCssCycleId(UUID cssCycleId) {
        return steamInjectionRepository.findByCssCycleId(cssCycleId).stream()
                .map(steamInjectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public SteamInjectionResponse update(UUID id, SteamInjectionRequest request) {
        SteamInjection injection = steamInjectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SteamInjection not found with id: " + id));

        CssCycle cycle = cssCycleRepository.findById(request.getCssCycleId())
                .orElseThrow(() -> new ResourceNotFoundException("CssCycle not found with id: " + request.getCssCycleId()));

        steamInjectionMapper.updateEntityFromRequest(request, injection);
        injection.setCssCycle(cycle);
        SteamInjection updated = steamInjectionRepository.save(injection);
        return steamInjectionMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!steamInjectionRepository.existsById(id)) {
            throw new ResourceNotFoundException("SteamInjection not found with id: " + id);
        }
        steamInjectionRepository.deleteById(id);
    }
}
