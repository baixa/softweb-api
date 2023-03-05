package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.category.CategoryDto;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById (@PathVariable(name = "id") String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return Objects.isNull(category) ? new ResponseEntity<>(null, HttpStatus.NOT_FOUND) :
                ResponseEntity.ok(new CategoryDto(category));
    }

    @GetMapping
    public ResponseEntity<?> getCategories () {
        List<Category> categories = categoryService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(CategoryDto::new).toList();
        return ResponseEntity.ok(categoryDtos);
    }
}
