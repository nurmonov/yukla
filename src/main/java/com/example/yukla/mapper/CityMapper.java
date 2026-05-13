package com.example.yukla.mapper;

import com.example.yukla.dto.CityResponse;
import com.example.yukla.entity.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CityMapper {

    @Mapping(target = "region", source = "region.nameUz")
    CityResponse toResponse(City city);

    List<CityResponse> toResponseList(List<City> cities);
}