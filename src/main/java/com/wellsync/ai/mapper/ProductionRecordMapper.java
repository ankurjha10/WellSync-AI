package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.ProductionRecordRequest;
import com.wellsync.ai.dto.ProductionRecordResponse;
import com.wellsync.ai.entity.ProductionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductionRecordMapper {

    @Mapping(target = "well", ignore = true)
    ProductionRecord toEntity(ProductionRecordRequest request);

    @Mapping(source = "well.id", target = "wellId")
    ProductionRecordResponse toResponse(ProductionRecord entity);
}
