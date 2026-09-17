package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.ReservoirRequest;
import com.wellsync.ai.dto.ReservoirResponse;
import com.wellsync.ai.entity.Reservoir;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReservoirMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Reservoir toEntity(ReservoirRequest request);

    ReservoirResponse toResponse(Reservoir entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(ReservoirRequest request, @MappingTarget Reservoir entity);
}
