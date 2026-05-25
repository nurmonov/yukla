package com.example.yukla.mapper;

import com.example.yukla.dto.LoadRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.Load;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoadMapper {

    @Mapping(target = "shipper", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "fromDistrict", ignore = true)
    @Mapping(target = "toDistrict", ignore = true)
    Load toEntity(LoadRequest request);

    @Mapping(target = "shipperId", source = "shipper.id")
    @Mapping(target = "shipperName", source = "shipper.displayName")
    @Mapping(target = "shipperPhone", source = "shipper.phone")
    @Mapping(target = "categoryName", source = "category.nameUz")
    @Mapping(target = "fromDistrictName", source = "fromDistrict.nameUz")
    @Mapping(target = "toDistrictName", source = "toDistrict.nameUz")
    LoadResponse toResponse(Load load);

    List<LoadResponse> toResponseList(List<Load> loads);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(LoadRequest request, @MappingTarget Load load);
}