package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.ControlCommandRequest;
import com.wellsync.ai.dto.ControlCommandResponse;
import com.wellsync.ai.entity.ControlCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ControlCommandMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestedAt", ignore = true)
    @Mapping(target = "executedAt", ignore = true)
    @Mapping(target = "failureReason", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "recommendation", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    ControlCommand toEntity(ControlCommandRequest request);

    @Mapping(source = "well.id", target = "wellId")
    @Mapping(source = "recommendation.id", target = "recommendationId")
    @Mapping(source = "requestedBy.id", target = "requestedById")
    ControlCommandResponse toResponse(ControlCommand entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestedAt", ignore = true)
    @Mapping(target = "executedAt", ignore = true)
    @Mapping(target = "failureReason", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "recommendation", ignore = true)
    @Mapping(target = "requestedBy", ignore = true)
    void updateEntityFromRequest(ControlCommandRequest request, @MappingTarget ControlCommand entity);
}
