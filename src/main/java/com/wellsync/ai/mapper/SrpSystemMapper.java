package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SrpSystemRequest;
import com.wellsync.ai.dto.SrpSystemResponse;
import com.wellsync.ai.entity.SrpSystem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SrpSystemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "srpOperatingConfig", ignore = true)
    SrpSystem toEntity(SrpSystemRequest request);

    @Mapping(source = "well.id", target = "wellId")
    SrpSystemResponse toResponse(SrpSystem entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "srpOperatingConfig", ignore = true)
    void updateEntityFromRequest(SrpSystemRequest request, @MappingTarget SrpSystem entity);
}
