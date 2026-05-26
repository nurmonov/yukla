package com.example.yukla.mapper;

import com.example.yukla.dto.CategoryRequest;
import com.example.yukla.dto.CategoryResponse;
import com.example.yukla.entity.Category;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    // Eng muhim qator — code maydonini to'g'ri map qilish
    @Mapping(target = "code", source = "code")
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CategoryRequest request, @MappingTarget Category category);
}