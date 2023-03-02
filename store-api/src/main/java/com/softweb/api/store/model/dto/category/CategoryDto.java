package com.softweb.api.store.model.dto.category;

import com.softweb.api.store.model.entities.Category;
import lombok.Data;

import java.io.Serializable;

/**
 * A DTO for the {@link Category} entity
 */
@Data
public class CategoryDto implements Serializable {
    private final Long id;
    private final String name;

    public CategoryDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}