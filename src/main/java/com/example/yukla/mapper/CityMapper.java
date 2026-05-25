package com.example.yukla.mapper;

import com.example.yukla.dto.CityRequest;
import com.example.yukla.dto.CityResponse;
import com.example.yukla.entity.City;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    @Mapping(target = "region", ignore = true)   // Regionni qo'lda set qilamiz
    City toEntity(CityRequest request);

    @Mapping(target = "regionNameUz", source = "region.nameUz")
    @Mapping(target = "regionId", source = "region.id")
    CityResponse toResponse(City city);

    List<CityResponse> toResponseList(List<City> cities);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CityRequest request, @MappingTarget City city);
}