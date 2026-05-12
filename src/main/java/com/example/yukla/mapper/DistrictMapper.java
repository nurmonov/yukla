package com.example.yukla.mapper;

import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.entity.District;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistrictMapper {

    @Mapping(source = "city.nameUz", target = "cityName")
    @Mapping(source = "region.nameUz", target = "regionName")
    DistrictResponse toResponse(District district);

    List<DistrictResponse> toResponseList(List<District> districts);
}
