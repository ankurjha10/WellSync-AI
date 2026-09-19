package com.wellsync.ai.control;

import com.wellsync.ai.dto.ControlCommandRequest;
import com.wellsync.ai.entity.ControlCommand;
import com.wellsync.ai.entity.Well;
import com.wellsync.ai.entity.enums.CommandStatus;
import com.wellsync.ai.entity.enums.CommandType;
import com.wellsync.ai.entity.enums.CommandSource;
import com.wellsync.ai.repository.ControlCommandRepository;
import com.wellsync.ai.repository.WellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ControlSafetyEngine {

    private final ControlCommandRepository controlCommandRepository;
    private final WellRepository wellRepository;

    /**
     * Validates and registers a control command if it is safe to execute.
     */
    @Transactional
    public ControlCommand requestCommandExecution(ControlCommandRequest request) {
        Well well = wellRepository.findById(request.getWellId())
                .orElseThrow(() -> new IllegalArgumentException("Well not found"));

        if (!"ACTIVE".equals(well.getStatus().name()) && !"WARNING".equals(well.getStatus().name())) {
            throw new IllegalStateException("Cannot execute commands on a well that is not ACTIVE or WARNING (Current: " + well.getStatus() + ")");
        }

        // Safety rules based on command type
        if (request.getCommandType() == CommandType.SET_RPM) {
            validateRpmSafety(request.getRequestedValue());
        } else {
            // Other commands pass through for now
        }

        ControlCommand command = new ControlCommand();
        command.setWell(well);
        command.setCommandType(request.getCommandType());
        command.setRequestedValue(request.getRequestedValue());
        command.setUnit(request.getUnit());
        command.setStatus(CommandStatus.PENDING);
        command.setSource(CommandSource.OPERATOR);
        // We will skip user relation validation for the MVP to keep it simple
        return controlCommandRepository.save(command);
    }

    private void validateRpmSafety(Double rpm) {
        if (rpm == null) throw new IllegalArgumentException("RPM value is required");
        if (rpm < 3.0 || rpm > 12.0) {
            throw new IllegalArgumentException("RPM must be between 3.0 and 12.0 for Baghewala SRPs");
        }
    }

    private void validateSteamInjection(Double rate) {
        if (rate == null) throw new IllegalArgumentException("Steam injection rate is required");
        if (rate <= 0 || rate > 500) {
            throw new IllegalArgumentException("Steam injection rate must be between 0 and 500 bbl/d equivalent");
        }
    }

    /**
     * Used by the Python Simulator to poll for pending commands.
     */
    @Transactional
    public Optional<ControlCommand> getNextPendingCommand(UUID wellId) {
        return controlCommandRepository.findAll().stream()
                .filter(c -> c.getWell().getId().equals(wellId) && c.getStatus() == CommandStatus.PENDING)
                .findFirst();
    }
    
    @Transactional
    public void markCommandExecuted(UUID commandId) {
        controlCommandRepository.findById(commandId).ifPresent(c -> {
            c.setStatus(CommandStatus.EXECUTED);
            controlCommandRepository.save(c);
        });
    }
}
