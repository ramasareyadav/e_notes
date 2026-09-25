package com.e_notes.controller;

import com.e_notes.dto.CategoryDto;
import com.e_notes.dto.CategoryResponse;
import com.e_notes.service.CategoryService;
import com.e_notes.util.CommonUtil;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto categorydto){
        boolean saved = categoryService.saveCategory(categorydto);
        if (saved) {
          return CommonUtil.createBuildResponseMessage("saved success",HttpStatus.CREATED);
          //  return new ResponseEntity<>("saved success", HttpStatus.CREATED);
        } else {
            return CommonUtil.createErrorResponseMessage("not saved ",HttpStatus.INTERNAL_SERVER_ERROR);
           // return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allCategory")
    public ResponseEntity<?> getAllCategory() {
        List<CategoryDto> allCategory = categoryService.getAllCategory();
        if (CollectionUtils.isEmpty(allCategory)) {
            return ResponseEntity.noContent().build();
        } else {
            return CommonUtil.createBuildResponse(allCategory,HttpStatus.OK);
            //return new ResponseEntity<>(allCategory, HttpStatus.OK);
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

        return ResponseEntity.ok(categoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PutMapping
    public ResponseEntity<String> updateCategory(@RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(categoryDto);
        return ResponseEntity.ok("Category updated successfully");
    }
}