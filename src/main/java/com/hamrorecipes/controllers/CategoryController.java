package com.hamrorecipes.controllers;

import com.hamrorecipes.models.CategoryModel;
import com.hamrorecipes.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/hr")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/createCategory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryModel categoryModel) {
        return categoryService.createCategory(categoryModel);
    }

    @PostMapping("/updateCategory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryModel categoryModel) {
        return categoryService.updateCategory(categoryModel);
    }

    @DeleteMapping("/deleteCategory")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<?> deleteCategory(@Valid @RequestBody CategoryModel categoryModel) {
        return categoryService.deleteCategory(categoryModel);
    }
}
