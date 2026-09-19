package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.FailureEventRequest;
import com.wellsync.ai.dto.FailureEventResponse;
import com.wellsync.ai.entity.FailureEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FailureEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    FailureEvent toEntity(FailureEventRequest request);

    @Mapping(source = "well.id", target = "wellId")
    FailureEventResponse toResponse(FailureEvent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(FailureEventRequest request, @MappingTarget FailureEvent entity);
}
