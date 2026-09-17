package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SrpSystemRequest;
import com.wellsync.ai.dto.SrpSystemResponse;
import com.wellsync.ai.entity.SrpSystem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SrpSystemMapper {

    @Mapping(target = "well", ignore = true)
    @Mapping(target = "srpOperatingConfig", ignore = true)
    SrpSystem toEntity(SrpSystemRequest request);

    @Mapping(source = "well.id", target = "wellId")
    SrpSystemResponse toResponse(SrpSystem entity);

    @Mapping(target = "well", ignore = true)
    @Mapping(target = "srpOperatingConfig", ignore = true)
    void updateEntityFromRequest(SrpSystemRequest request, @MappingTarget SrpSystem entity);
}
