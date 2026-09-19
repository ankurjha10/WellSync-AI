package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.RecommendationRequest;
import com.wellsync.ai.dto.RecommendationResponse;
import com.wellsync.ai.entity.Recommendation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecommendationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    @Mapping(target = "actedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    Recommendation toEntity(RecommendationRequest request);

    @Mapping(source = "well.id", target = "wellId")
    RecommendationResponse toResponse(Recommendation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "generatedAt", ignore = true)
    @Mapping(target = "actedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(RecommendationRequest request, @MappingTarget Recommendation entity);
}
