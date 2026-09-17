package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SensorConfigRequest;
import com.wellsync.ai.dto.SensorConfigResponse;
import com.wellsync.ai.entity.SensorConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SensorConfigMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastSeenAt", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    @Mapping(target = "well", ignore = true)
    SensorConfig toEntity(SensorConfigRequest request);

    @Mapping(source = "well.id", target = "wellId")
    SensorConfigResponse toResponse(SensorConfig entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastSeenAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(SensorConfigRequest request, @MappingTarget SensorConfig entity);
}
