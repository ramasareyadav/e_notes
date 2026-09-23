package com.e_notes.service.impl;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.CategoryResponse;
import com.e_notes.model.Category;
import com.e_notes.repository.CategoryRepository;
import com.e_notes.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {

        Category category = modelMapper.map(categoryDto, Category.class);
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(LocalDateTime.now());
        Category saveCategory = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(saveCategory)) {
            return false;
        }
        return true;
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> allCategory = categoryRepository.findAll();
        return allCategory.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .toList();
    }

    @Override
    public List<CategoryResponse> getActiveCategoey() {
        List<Category> categories = categoryRepository.findByIsActiveTrue();
        List<CategoryResponse> categoryList = categories.stream().map(category -> modelMapper.map(category, CategoryResponse.class))
                .toList();
        return categoryList;
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return null;
        }
        Category category = optionalCategory.get();
        if (Boolean.TRUE.equals(category.getIsDeleted())) {
            return null;
        }
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public boolean deleteCategory(Long id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return false;
        }
        Category category = optionalCategory.get(); // Soft delete category.setIsDeleted(true); category.setIsActive(false); categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean updateCategory(CategoryDto categoryDto) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());
        if (optionalCategory.isEmpty()) {
            return false;
        }
        Category category = optionalCategory.get();
        // Check soft deleted category
        if (Boolean.TRUE.equals(category.getIsDeleted())) {
            return false;
        }
        modelMapper.map(categoryDto, category);
        category.setUpdatedBY(1);
        category.setCreatedOn(LocalDateTime.now());
        // Save updated category
        Category updateCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(updateCategory);

    }
}
