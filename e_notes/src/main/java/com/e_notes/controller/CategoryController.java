package com.e_notes.controller;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.CategoryResponse;
import com.e_notes.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/savecategory")
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categorydto) {
        boolean saved = categoryService.saveCategory(categorydto);
        if (saved) {
            return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allCategory")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/active-category")
    public ResponseEntity<?> getActiveCategory() {
        List<CategoryResponse> allCategory = categoryService.getActiveCategoey();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(allCategory, HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.getCategoryById(id);
        if (categoryDto == null) {
            return new ResponseEntity<>("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return new ResponseEntity<>("Category deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto categoryDto) {
        boolean updated = categoryService.updateCategory(categoryDto);
        if (updated) {
            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Category not found with id: " + categoryDto.getId(), HttpStatus.NOT_FOUND);

    }
}