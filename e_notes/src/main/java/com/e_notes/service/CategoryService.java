package com.e_notes.service;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.CategoryResponse;
import com.e_notes.model.Category;

import java.util.List;
import java.util.zip.DataFormatException;

public interface CategoryService {

    boolean saveCategory(CategoryDto categoryDto);

    List<CategoryDto> getAllCategory();

    List<CategoryResponse> getActiveCategoey();

    CategoryDto getCategoryById(Long id);

    boolean deleteCategory(Long id);

    boolean updateCategory(CategoryDto categoryDto);

    List<CategoryDto> findByIsDeletedFalse();
}
