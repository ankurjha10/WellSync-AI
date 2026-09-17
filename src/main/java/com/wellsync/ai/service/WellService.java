package com.wellsync.ai.service;

import com.wellsync.ai.entity.Well;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellService {

    private final WellRepository wellRepository;

    public List<Well> getAllWells(){
        return wellRepository.findAll();
    }

    public Well getWellByWellCode(String wellCode){
        return wellRepository.findByWellCode(wellCode).orElseThrow(() -> new RuntimeException("well code not found"));
    }

    public Well createWell(Well well){
        return wellRepository.save(well);
    }
}
