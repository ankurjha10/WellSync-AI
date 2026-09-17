package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.CssCycleRequest;
import com.wellsync.ai.dto.CssCycleResponse;
import com.wellsync.ai.entity.CssCycle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CssCycleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "steamInjections", ignore = true)
    CssCycle toEntity(CssCycleRequest request);

    @Mapping(source = "well.id", target = "wellId")
    CssCycleResponse toResponse(CssCycle entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "steamInjections", ignore = true)
    void updateEntityFromRequest(CssCycleRequest request, @MappingTarget CssCycle entity);
}
