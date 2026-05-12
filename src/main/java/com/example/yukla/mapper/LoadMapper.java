package com.example.yukla.mapper;

import com.example.yukla.dto.LoadCreateRequest;
import com.example.yukla.dto.LoadResponse;
import com.example.yukla.entity.District;
import com.example.yukla.entity.Load;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {CategoryMapper.class, DistrictMapper.class})
public interface LoadMapper {

    @Mapping(source = "category", target = "category")
    @Mapping(source = "fromDistrict", target = "fromDistrictName", qualifiedByName = "districtName")
    @Mapping(source = "toDistrict", target = "toDistrictName", qualifiedByName = "districtName")
    LoadResponse toResponse(Load load);

    List<LoadResponse> toResponseList(List<Load> loads);

    @Mapping(target = "shipper", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    @Mapping(target = "viewsCount", ignore = true)
    Load toEntity(LoadCreateRequest request);

    // District nomini olish uchun yordamchi metod
    @Named("districtName")
    default String getDistrictName(District district) {
        return district != null ? district.getNameUz() : null;
    }
}