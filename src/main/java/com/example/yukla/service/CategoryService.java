package com.example.yukla.service;

import com.example.yukla.dto.CategoryResponse;
import com.example.yukla.mapper.CategoryMapper;
import com.example.yukla.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryResponse> getAllActiveCategories() {
        return categoryMapper.toResponseList(categoryRepository.findByIsActiveTrue());
    }
}
