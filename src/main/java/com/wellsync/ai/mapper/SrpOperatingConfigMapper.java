package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SrpOperatingConfigRequest;
import com.wellsync.ai.dto.SrpOperatingConfigResponse;
import com.wellsync.ai.entity.SrpOperatingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SrpOperatingConfigMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "srpSystem", ignore = true)
    SrpOperatingConfig toEntity(SrpOperatingConfigRequest request);

    @Mapping(source = "srpSystem.id", target = "srpSystemId")
    SrpOperatingConfigResponse toResponse(SrpOperatingConfig entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "srpSystem", ignore = true)
    void updateEntityFromRequest(SrpOperatingConfigRequest request, @MappingTarget SrpOperatingConfig entity);
}
