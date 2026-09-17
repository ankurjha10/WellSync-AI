package com.wellsync.ai.mapper;

import com.wellsync.ai.dto.ProductionRecordRequest;
import com.wellsync.ai.dto.ProductionRecordResponse;
import com.wellsync.ai.entity.ProductionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductionRecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "well", ignore = true)
    ProductionRecord toEntity(ProductionRecordRequest request);

    @Mapping(source = "well.id", target = "wellId")
    ProductionRecordResponse toResponse(ProductionRecord entity);
}
