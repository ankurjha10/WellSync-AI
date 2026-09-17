package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.CompletionRequest;
import com.wellsync.ai.dto.CompletionResponse;
import com.wellsync.ai.entity.Completion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompletionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    Completion toEntity(CompletionRequest request);

    @Mapping(source = "well.id", target = "wellId")
    CompletionResponse toResponse(Completion entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(CompletionRequest request, @MappingTarget Completion entity);
}
