package com.e_notes.service;

import com.e_notes.model.Category;

import java.util.List;

public interface CategoryService {

    boolean saveCategory(Category category);

    List<Category> getAllCategory();
}
