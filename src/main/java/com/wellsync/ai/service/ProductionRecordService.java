package com.wellsync.ai.service;

import com.wellsync.ai.dto.ProductionRecordRequest;
import com.wellsync.ai.dto.ProductionRecordResponse;
import com.wellsync.ai.entity.ProductionRecord;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.mapper.ProductionRecordMapper;
import com.wellsync.ai.repository.ProductionRecordRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductionRecordService {

    private final ProductionRecordRepository productionRecordRepository;
    private final WellRepository wellRepository;
    private final ProductionRecordMapper productionRecordMapper;

    @Transactional
    public ProductionRecordResponse create(ProductionRecordRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new ResourceNotFoundException("Well not found with id: " + request.getWellId()));

        ProductionRecord record = productionRecordMapper.toEntity(request);
        record.setWell(well);
        ProductionRecord saved = productionRecordRepository.saveAndFlush(record);
        return productionRecordMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public ProductionRecordResponse getById(UUID id) {
        ProductionRecord record = productionRecordRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ProductionRecord not found with id: " + id));
        return productionRecordMapper.toResponse(record);
    }

    @Transactional(readOnly = true)
    public List<ProductionRecordResponse> getAllByWellId(UUID wellId) {
        return productionRecordRepository.findByWellIdOrderByRecordedAtDesc(wellId).stream()
                .map(productionRecordMapper::toResponse)
                .collect(Collectors.toList());
    }
}
