package com.e_notes.service.impl;

import com.e_notes.model.Category;
import com.e_notes.repository.CategoryRepository;
import com.e_notes.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean saveCategory(Category category) {
        category.setIsDeleted(false);
        category.setCreatedBy(1);
        category.setCreatedOn(new Date());
        Category saveCategory = categoryRepository.save(category);
        if (ObjectUtils.isEmpty(saveCategory)) {
            return false;
        }
        return true;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
}
