package com.e_notes.service.impl;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.CategoryResponse;
import com.e_notes.exception.ResourceNotFoundException;
import com.e_notes.model.Category;
import com.e_notes.repository.CategoryRepository;
import com.e_notes.service.CategoryService;
import com.e_notes.util.Validation;
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
    private final Validation validation;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper, Validation validation) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validation = validation;
    }

    @Override
    public boolean saveCategory(CategoryDto categoryDto) {

        validation.categoryValidation(categoryDto);

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
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("category not found id:" + id));

        if (Boolean.TRUE.equals(optionalCategory.getIsDeleted())) {
            throw new ResourceNotFoundException("category not found with id:" + optionalCategory.getId());
        }
        return modelMapper.map(optionalCategory, CategoryDto.class);
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category optionalCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "category not found id" + id
                ));
        if (Boolean.TRUE.equals(optionalCategory.getIsDeleted())) {
            throw new ResourceNotFoundException("Category already deleted with id: " + id);
        }
        optionalCategory.setIsDeleted(true);
        optionalCategory.setIsActive(false);
        categoryRepository.save(optionalCategory);
        return true;
    }

    @Override
    public boolean updateCategory(CategoryDto categoryDto) {

        Category category = categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "category not found with id:" + categoryDto.getId()
                ));
        // Check soft deleted category
        if (Boolean.TRUE.equals(category.getIsDeleted())) {
            throw new ResourceNotFoundException("category not found with id:" + categoryDto.getId());
        }
        modelMapper.map(categoryDto, category);
        category.setUpdatedBY(1);
        category.setCreatedOn(LocalDateTime.now());
        // Save updated category
        Category updateCategory = categoryRepository.save(category);
        return !ObjectUtils.isEmpty(updateCategory);

    }
}
