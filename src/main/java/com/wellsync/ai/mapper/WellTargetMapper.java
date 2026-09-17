package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.WellTargetRequest;
import com.wellsync.ai.dto.WellTargetResponse;
import com.wellsync.ai.entity.WellTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface WellTargetMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    WellTarget toEntity(WellTargetRequest request);

    @Mapping(source = "well.id", target = "wellId")
    WellTargetResponse toResponse(WellTarget entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(WellTargetRequest request, @MappingTarget WellTarget entity);
}
