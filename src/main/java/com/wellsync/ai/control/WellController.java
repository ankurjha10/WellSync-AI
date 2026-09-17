package com.wellsync.ai.control;

import com.wellsync.ai.entity.Well;
import com.wellsync.ai.service.WellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wells")
@RequiredArgsConstructor
public class WellController {

    private final WellService  wellService;

    @GetMapping
    public List<Well> getAllWells(){
        return wellService.getAllWells();
    }

    @GetMapping("/{wellCode}")
    public ResponseEntity<Well> getWell(@PathVariable String wellCode){
        return ResponseEntity.ok(wellService.getWellByWellCode(wellCode));
    }

    @PostMapping
    public Well createWell(@RequestBody Well well){
        return wellService.createWell(well);
    }
}
