package com.wellsync.ai.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellsync.ai.dto.ControlCommandRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommandProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final String TOPIC = "control.commands";

    public void sendCommand(ControlCommandRequest command) {
        try {
            String payload = objectMapper.writeValueAsString(command);
            kafkaTemplate.send(TOPIC, command.getWellId().toString(), payload);
            log.info("Sent Control Command to Kafka for Well {}: {}", command.getWellId(), payload);
        } catch (Exception e) {
            log.error("Failed to send Control Command to Kafka for Well {}", command.getWellId(), e);
        }
    }
}
