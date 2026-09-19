package com.wellsync.ai.recommendation;

import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.RecommendationRequest;
import com.wellsync.ai.entity.enums.RecommendationStatus;
import com.wellsync.ai.entity.enums.RecommendationType;
import com.wellsync.ai.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendationEngine {

    private final RecommendationService recommendationService;

    public void evaluateRecommendations(WellDigitalTwinState state, List<String> riskFactors) {
        if (state.getCurrentRiskScore() < 60) {
            return;
        }

        try {
            if (state.getRodLoadLbs() != null && state.getRodLoadLbs() > 15000.0 &&
                state.getPumpRpm() != null && state.getPumpRpm() > 8.0) {
                
                String reason = "High viscosity and elevated rod load indicate increased mechanical risk. Reducing RPM is recommended to reduce rod loading.";
                generateRecommendation(state, RecommendationType.REDUCE_RPM, state.getPumpRpm(), 8.0, "RPM", reason, riskFactors);
            }

            if (state.getTemperatureC() != null && state.getTemperatureC() < 50.0) {
                String reason = "Reservoir temperature has dropped below critical mobility threshold. A new CSS steam injection cycle is recommended.";
                generateRecommendation(state, RecommendationType.OPTIMIZE_STEAM, state.getTemperatureC(), 250.0, "Celsius", reason, riskFactors);
            }

        } catch (Exception e) {
            log.error("Failed to generate recommendation for well {}: {}", state.getWellId(), e.getMessage());
        }
    }

    private void generateRecommendation(WellDigitalTwinState state, RecommendationType type, Double current, Double recommended, 
                                        String unit, String reason, List<String> factors) {
        
        RecommendationRequest req = new RecommendationRequest();
        req.setWellId(state.getWellId());
        req.setRecommendationType(type);
        req.setCurrentValue(current);
        req.setRecommendedValue(recommended);
        req.setUnit(unit);
        req.setRiskScore((double) state.getCurrentRiskScore());
        req.setReason(reason);
        req.setFactors(String.join("; ", factors));
        req.setStatus(RecommendationStatus.PENDING);

        recommendationService.create(req);
        log.info("Generated Recommendation {} for Well {}", type, state.getWellId());
    }
}
