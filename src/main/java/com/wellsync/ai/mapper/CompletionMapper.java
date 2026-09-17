package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.CompletionRequest;
import com.wellsync.ai.dto.CompletionResponse;
import com.wellsync.ai.entity.Completion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompletionMapper {
    
    @Mapping(target = "well", ignore = true)
    Completion toEntity(CompletionRequest request);

    @Mapping(source = "well.id", target = "wellId")
    CompletionResponse toResponse(Completion entity);

    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(CompletionRequest request, @MappingTarget Completion entity);
}
