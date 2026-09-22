package com.wellsync.ai.service;

import com.wellsync.ai.digitaltwin.DigitalTwinStateService;
import com.wellsync.ai.digitaltwin.WellDigitalTwinState;
import com.wellsync.ai.dto.CopilotResponse;
import com.wellsync.ai.entity.Alert;
import com.wellsync.ai.exception.ResourceNotFoundException;
import com.wellsync.ai.repository.AlertRepository;
import com.wellsync.ai.entity.Recommendation;
import com.wellsync.ai.repository.RecommendationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CopilotService {

    private static final String SYSTEM_PROMPT = """
            You are WellSync AI Copilot — an expert petroleum engineer and SCADA operations advisor \
            for heavy-oil Cyclic Steam Stimulation (CSS) wells using Sucker Rod Pump (SRP) systems \
            at the Baghewala field, India.

            STRICT RULES:
            1. You ONLY answer questions related to well operations, SCADA telemetry, CSS/SRP systems, \
               production optimization, risk analysis, reservoir management, pump mechanics, rod loading, \
               steam injection, oil viscosity, and related petroleum engineering topics.
            2. If the user asks ANY question that is NOT related to well operations, petroleum engineering, \
               or the SCADA system — such as general knowledge, coding, weather, politics, sports, jokes, \
               personal opinions, or anything outside your domain — you MUST politely refuse. \
               Reply: "I'm WellSync AI Copilot, specialized in well operations and SCADA monitoring. \
               I can only assist with questions related to your well systems, telemetry, and production operations."
            3. NEVER make up sensor values. Only use the data provided to you in the context.
            4. Keep responses concise, actionable, and operator-friendly. Use bullet points when listing \
               multiple factors.
            5. When explaining risks, always tie the explanation back to the physics: temperature → viscosity → \
               rod load → pump efficiency chain.
            """;

    private final ChatClient chatClient;
    private final AlertRepository alertRepository;
    private final RecommendationRepository recommendationRepository;
    private final DigitalTwinStateService digitalTwinStateService;

    public CopilotService(ChatClient.Builder chatClientBuilder,
                          AlertRepository alertRepository,
                          RecommendationRepository recommendationRepository,
                          DigitalTwinStateService digitalTwinStateService) {
        this.chatClient = chatClientBuilder
                .defaultSystem(SYSTEM_PROMPT)
                .build();
        this.alertRepository = alertRepository;
        this.recommendationRepository = recommendationRepository;
        this.digitalTwinStateService = digitalTwinStateService;
    }

    /**
     * Generates an AI-powered root-cause explanation for a given alert.
     */
    public CopilotResponse explainAlert(UUID alertId) {
        Alert alert = alertRepository.findById(alertId)
                .orElseThrow(() -> new ResourceNotFoundException("Alert not found with id: " + alertId));

        UUID wellId = alert.getWell().getId();
        WellDigitalTwinState state = digitalTwinStateService.getState(wellId);

        String userPrompt = buildAlertExplanationPrompt(alert, state);

        log.info("Requesting AI explanation for alert {} on well {}", alertId, wellId);

        String aiResponse = chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();

        return new CopilotResponse(aiResponse);
    }

    /**
     * Handles a free-form chat message from the operator, with live Digital Twin context.
     */

    public CopilotResponse explainRecommendation(UUID recommendationId) {
        Recommendation rec = recommendationRepository.findById(recommendationId)
                .orElseThrow(() -> new ResourceNotFoundException("Recommendation not found with id: " + recommendationId));

        UUID wellId = rec.getWell().getId();
        WellDigitalTwinState state = digitalTwinStateService.getState(wellId);

        String userPrompt = buildRecommendationExplanationPrompt(rec, state);

        log.info("Requesting AI explanation for recommendation {} on well {}", recommendationId, wellId);

        String aiResponse = chatClient.prompt()
                .user(userPrompt)
                .call()
                .content();

        return new CopilotResponse(aiResponse);
    }

    public CopilotResponse chat(UUID wellId, String userMessage) {
        WellDigitalTwinState state = digitalTwinStateService.getState(wellId);

        String contextualPrompt = buildChatPrompt(state, userMessage);

        log.info("Copilot chat for well {}: {}", wellId, userMessage);

        String aiResponse = chatClient.prompt()
                .user(contextualPrompt)
                .call()
                .content();

        return new CopilotResponse(aiResponse);
    }

    private String buildAlertExplanationPrompt(Alert alert, WellDigitalTwinState state) {
        return String.format("""
                An alert has been triggered on the well. Analyze the alert and the current Digital Twin \
                telemetry data below, then provide a clear, concise root-cause explanation for the operator.

                --- ALERT DETAILS ---
                Alert Type: %s
                Severity: %s
                Title: %s
                Message: %s
                Risk Score: %s
                Created At: %s

                --- CURRENT DIGITAL TWIN STATE ---
                %s

                Provide:
                1. A plain-language root-cause explanation (why did this alert fire?)
                2. The chain of physical causation (e.g., temperature drop → viscosity increase → rod load spike)
                3. Recommended immediate operator action
                """,
                alert.getAlertType(),
                alert.getSeverity(),
                alert.getTitle(),
                alert.getMessage(),
                alert.getRiskScore(),
                alert.getCreatedAt(),
                formatState(state)
        );
    }


    private String buildRecommendationExplanationPrompt(Recommendation rec, WellDigitalTwinState state) {
        return String.format("""
                A system recommendation has been generated for the well. Analyze the recommendation and the current \
                Digital Twin telemetry data below, then provide a clear, concise justification for the operator.

                --- RECOMMENDATION DETAILS ---
                Type: %s
                Title: %s
                Message: %s
                Command Type: %s
                Target Value: %s %s

                --- CURRENT DIGITAL TWIN STATE ---
                %s

                Provide:
                1. A plain-language explanation of why this action is recommended.
                2. What physical impact this action will have on the well.
                3. The potential risks if this recommendation is ignored.
                """,
                rec.getRecommendationType(),
                rec.getRecommendationType(),
                rec.getReason(),
                rec.getRecommendationType(),
                rec.getRecommendedValue(),
                rec.getUnit(),
                formatState(state)
        );
    }

    private String buildChatPrompt(WellDigitalTwinState state, String userMessage) {
        return String.format("""
                The operator is asking a question. Use the live Digital Twin state below to answer accurately.

                --- CURRENT DIGITAL TWIN STATE (Well: %s) ---
                %s

                --- OPERATOR QUESTION ---
                %s
                """,
                state.getWellId(),
                formatState(state),
                userMessage
        );
    }

    private String formatState(WellDigitalTwinState state) {
        return String.format("""
                Well ID: %s
                Last Updated: %s
                Reservoir Temperature: %s °C
                Pressure: %s PSI
                Oil Viscosity: %s cP
                Pump RPM: %s
                Rod Load: %s lbs
                SPM (Strokes Per Minute): %s
                VFD Frequency: %s Hz
                Stroke Length: %s in
                Pump Efficiency: %s %%
                Steam Pressure: %s PSI
                Steam Temperature: %s °C
                Production Rate: %s BOPD
                Cooling Rate: %s °C/hr
                Risk Score: %s / 100
                Risk Level: %s
                System Status: %s""",
                state.getWellId(),
                state.getLastUpdatedAt(),
                state.getTemperatureC(),
                state.getPressurePsi(),
                state.getViscosityCp(),
                state.getPumpRpm(),
                state.getRodLoadLbs(),
                state.getSpm(),
                state.getVfdFrequencyHz(),
                state.getStrokeLengthIn(),
                state.getPumpEfficiencyPercent(),
                state.getSteamPressurePsi(),
                state.getSteamTemperatureC(),
                state.getProductionRateBopd(),
                state.getCoolingRateCPerHour(),
                state.getCurrentRiskScore(),
                state.getRiskLevel(),
                state.getSystemStatus()
        );
    }
}
