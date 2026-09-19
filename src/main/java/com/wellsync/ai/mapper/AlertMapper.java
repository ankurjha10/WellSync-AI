package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.AlertRequest;
import com.wellsync.ai.dto.AlertResponse;
import com.wellsync.ai.entity.Alert;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlertMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "isAcknowledged", ignore = true)
    @Mapping(target = "acknowledgedBy", ignore = true)
    @Mapping(target = "acknowledgedAt", ignore = true)
    Alert toEntity(AlertRequest request);

    @Mapping(source = "well.id", target = "wellId")
    @Mapping(source = "acknowledgedBy.id", target = "acknowledgedById")
    AlertResponse toResponse(Alert entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    @Mapping(target = "acknowledged", ignore = true)
    @Mapping(target = "acknowledgedBy", ignore = true)
    @Mapping(target = "acknowledgedAt", ignore = true)
    void updateEntityFromRequest(AlertRequest request, @MappingTarget Alert entity);
}
