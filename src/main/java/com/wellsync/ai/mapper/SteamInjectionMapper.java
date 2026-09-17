package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.SteamInjectionRequest;
import com.wellsync.ai.dto.SteamInjectionResponse;
import com.wellsync.ai.entity.SteamInjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SteamInjectionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "cssCycle", ignore = true)
    SteamInjection toEntity(SteamInjectionRequest request);

    @Mapping(source = "cssCycle.id", target = "cssCycleId")
    SteamInjectionResponse toResponse(SteamInjection entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "cssCycle", ignore = true)
    void updateEntityFromRequest(SteamInjectionRequest request, @MappingTarget SteamInjection entity);
}
