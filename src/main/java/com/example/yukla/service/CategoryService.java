package com.example.yukla.service;

import com.example.yukla.dto.CategoryRequest;
import com.example.yukla.dto.CategoryResponse;
import com.example.yukla.entity.Category;
import com.example.yukla.mapper.CategoryMapper;
import com.example.yukla.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameUzIgnoreCase(request.getNameUz())) {
            throw new RuntimeException("Bu nomdagi kategoriya allaqachon mavjud");
        }

        Category category = categoryMapper.toEntity(request);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoriya topilmadi"));
        return categoryMapper.toResponse(category);
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAllByOrderByNameUzAsc().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public List<CategoryResponse> searchCategories(String keyword) {
        return categoryRepository.searchByName(keyword).stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategoriya topilmadi"));

        categoryMapper.updateEntity(request, category);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponse(saved);
    }

    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Kategoriya topilmadi");
        }
        categoryRepository.deleteById(id);
    }
}
