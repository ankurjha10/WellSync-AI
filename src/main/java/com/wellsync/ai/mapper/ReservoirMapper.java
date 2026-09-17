package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.ReservoirRequest;
import com.wellsync.ai.dto.ReservoirResponse;
import com.wellsync.ai.entity.Reservoir;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservoirMapper {
    Reservoir toEntity(ReservoirRequest request);
    ReservoirResponse toResponse(Reservoir entity);
    void updateEntityFromRequest(ReservoirRequest request, @MappingTarget Reservoir entity);
}
