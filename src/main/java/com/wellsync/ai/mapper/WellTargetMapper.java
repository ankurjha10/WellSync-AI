package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.WellTargetRequest;
import com.wellsync.ai.dto.WellTargetResponse;
import com.wellsync.ai.entity.WellTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WellTargetMapper {

    @Mapping(target = "well", ignore = true)
    WellTarget toEntity(WellTargetRequest request);

    @Mapping(source = "well.id", target = "wellId")
    WellTargetResponse toResponse(WellTarget entity);

    @Mapping(target = "well", ignore = true)
    void updateEntityFromRequest(WellTargetRequest request, @MappingTarget WellTarget entity);
}
