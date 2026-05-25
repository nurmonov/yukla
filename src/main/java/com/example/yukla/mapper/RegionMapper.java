package com.example.yukla.mapper;


import com.example.yukla.dto.RegionRequest;
import com.example.yukla.dto.RegionResponse;
import com.example.yukla.entity.Region;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegionMapper {

    Region toEntity(RegionRequest request);

    RegionResponse toResponse(Region region);

    List<RegionResponse> toResponseList(List<Region> regions);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(RegionRequest request, @MappingTarget Region region);
}
