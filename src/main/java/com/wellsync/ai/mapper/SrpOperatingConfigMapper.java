package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SrpOperatingConfigRequest;
import com.wellsync.ai.dto.SrpOperatingConfigResponse;
import com.wellsync.ai.entity.SrpOperatingConfig;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SrpOperatingConfigMapper {

    @Mapping(target = "srpSystem", ignore = true)
    SrpOperatingConfig toEntity(SrpOperatingConfigRequest request);

    @Mapping(source = "srpSystem.id", target = "srpSystemId")
    SrpOperatingConfigResponse toResponse(SrpOperatingConfig entity);

    @Mapping(target = "srpSystem", ignore = true)
    void updateEntityFromRequest(SrpOperatingConfigRequest request, @MappingTarget SrpOperatingConfig entity);
}
