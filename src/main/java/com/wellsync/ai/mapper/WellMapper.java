package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.WellRequest;
import com.wellsync.ai.dto.WellResponse;
import com.wellsync.ai.entity.Well;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WellMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "reservoir", ignore = true)
    @Mapping(target = "completion", ignore = true)
    @Mapping(target = "srpSystem", ignore = true)
    @Mapping(target = "wellTarget", ignore = true)
    Well toEntity(WellRequest request);

    @Mapping(source = "reservoir.id", target = "reservoirId")
    WellResponse toResponse(Well entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "reservoir", ignore = true)
    @Mapping(target = "completion", ignore = true)
    @Mapping(target = "srpSystem", ignore = true)
    @Mapping(target = "wellTarget", ignore = true)
    void updateEntityFromRequest(WellRequest request, @MappingTarget Well entity);
}
