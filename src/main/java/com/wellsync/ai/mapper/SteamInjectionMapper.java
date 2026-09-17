package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SteamInjectionRequest;
import com.wellsync.ai.dto.SteamInjectionResponse;
import com.wellsync.ai.entity.SteamInjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SteamInjectionMapper {

    @Mapping(target = "cssCycle", ignore = true)
    SteamInjection toEntity(SteamInjectionRequest request);

    @Mapping(source = "cssCycle.id", target = "cssCycleId")
    SteamInjectionResponse toResponse(SteamInjection entity);

    @Mapping(target = "cssCycle", ignore = true)
    void updateEntityFromRequest(SteamInjectionRequest request, @MappingTarget SteamInjection entity);
}
