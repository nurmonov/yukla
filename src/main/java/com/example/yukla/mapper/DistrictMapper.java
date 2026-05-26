package com.example.yukla.mapper;

import com.example.yukla.dto.DistrictRequest;
import com.example.yukla.dto.DistrictResponse;
import com.example.yukla.entity.District;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DistrictMapper {

    @Mapping(target = "region", ignore = true)   // Regionni qo'lda set qilamiz
    District toEntity(DistrictRequest request);

    @Mapping(target = "regionNameUz", source = "region.nameUz")
    DistrictResponse toResponse(District district);

    List<DistrictResponse> toResponseList(List<District> districts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(DistrictRequest request, @MappingTarget District district);
}
