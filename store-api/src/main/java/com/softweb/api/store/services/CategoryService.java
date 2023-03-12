package com.softweb.api.store.services;

import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.model.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category getCategoryById (String categoryId) {
        return categoryRepository.findById(Long.valueOf(categoryId)).orElse(null);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
