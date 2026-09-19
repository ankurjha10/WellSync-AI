package com.wellsync.ai.service;

import com.wellsync.ai.dto.CssCycleRequest;
import com.wellsync.ai.dto.CssCycleResponse;
import com.wellsync.ai.entity.CssCycle;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.CssCycleMapper;
import com.wellsync.ai.repository.CssCycleRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CssCycleService {

    private final CssCycleRepository cssCycleRepository;
    private final WellRepository wellRepository;
    private final CssCycleMapper cssCycleMapper;

    @Transactional
    public CssCycleResponse create(CssCycleRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        CssCycle cycle = cssCycleMapper.toEntity(request);
        cycle.setWell(well);
        CssCycle saved = cssCycleRepository.saveAndFlush(cycle);
        return cssCycleMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public CssCycleResponse getById(UUID id) {
        CssCycle cycle = cssCycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CssCycle not found with id: " + id));
        return cssCycleMapper.toResponse(cycle);
    }

    @Transactional(readOnly = true)
    public List<CssCycleResponse> getAllByWellId(UUID wellId) {
        return cssCycleRepository.findByWellIdOrderByCycleNumberDesc(wellId).stream()
                .map(cssCycleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CssCycleResponse update(UUID id, CssCycleRequest request) {
        CssCycle cycle = cssCycleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CssCycle not found with id: " + id));

        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        cssCycleMapper.updateEntityFromRequest(request, cycle);
        cycle.setWell(well);
        CssCycle updated = cssCycleRepository.saveAndFlush(cycle);
        return cssCycleMapper.toResponse(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!cssCycleRepository.existsById(id)) {
            throw new ResourceNotFoundException("CssCycle not found with id: " + id);
        }
        cssCycleRepository.deleteById(id);
    }
}
