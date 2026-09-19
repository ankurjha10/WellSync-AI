package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SystemEventRequest;
import com.wellsync.ai.dto.SystemEventResponse;
import com.wellsync.ai.entity.SystemEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SystemEventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    SystemEvent toEntity(SystemEventRequest request);

    @Mapping(source = "well.id", target = "wellId")
    SystemEventResponse toResponse(SystemEvent entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(SystemEventRequest request, @MappingTarget SystemEvent entity);
}
