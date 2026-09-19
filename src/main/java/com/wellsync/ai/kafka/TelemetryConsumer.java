package com.wellsync.ai.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellsync.ai.digitaltwin.DigitalTwinStateService;
import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.TelemetryData;
import com.wellsync.ai.recommendation.RecommendationEngine;
import com.wellsync.ai.risk.RiskEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelemetryConsumer {

    private final ObjectMapper objectMapper;
    private final DigitalTwinStateService digitalTwinStateService;
    private final RiskEngine riskEngine;
    private final RecommendationEngine recommendationEngine;
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "telemetry.raw", groupId = "wellsync-group")
    public void consumeTelemetry(String message) {
        try {
            TelemetryData data = objectMapper.readValue(message, TelemetryData.class);
            
            // 1. Process telemetry and update digital twin state
            WellDigitalTwinState updatedState = digitalTwinStateService.updateState(data);
            List<String> riskFactors = riskEngine.evaluateRisk(updatedState);
            recommendationEngine.evaluateRecommendations(updatedState, riskFactors);
            
            // 2. Broadcast updated state to frontend via WebSocket
            messagingTemplate.convertAndSend("/topic/telemetry/" + updatedState.getWellId(), updatedState);
            
        } catch (Exception e) {
            log.error("Failed to process telemetry from Kafka: {}", e.getMessage(), e);
        }
    }
}
