package com.e_notes.controller;

import com.e_notes.model.Category;
import com.e_notes.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ategory")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/savecategory")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        boolean saved = categoryService.saveCategory(category);
        if (saved) {
            return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allCategory")
    public ResponseEntity<?> getAllCategory() {
        List<Category> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }

    }
}
