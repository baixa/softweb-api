package com.softweb.api.store.controllers;

import com.softweb.api.store.model.dto.category.CategoryDto;
import com.softweb.api.store.model.entities.Category;
import com.softweb.api.store.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for applications' categories
 * @see Category
 */
@RestController
@RequestMapping(value = "/v1/category")
@Tag(name = "Category", description = "API provides ability to manipulate applications' categories")
public class CategoryController {
    /**
     * Service, that provides ability to interaction with the Category entity
     */
    private final CategoryService categoryService;

    /**
     * Controller
     *
     * @param categoryService Service of categories
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Returns an object of the Category class whose id matches the one passed
     *
     * @param categoryId ID of requested category
     * @return Requested category
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Get category by it's id",
            description = "Returns an object of the Category class whose id matches the one passed"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns requested category",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "404", description = "Category with given id is absent", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getCategoryById (
            @Parameter(description = "Id of requested category")
            @PathVariable(name = "id") String categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return Objects.isNull(category) ? ResponseEntity.of(Optional.empty()) :
                ResponseEntity.ok(new CategoryDto(category));
    }

    /**
     * Returns a list of categories
     *
     * @return List of categories
     */
    @GetMapping
    @Operation(
            summary = "Get categories",
            description = "Returns all categories"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns full list of categories",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied", content = @Content) })
    public ResponseEntity<?> getCategories() {
        List<Category> categories = categoryService.getCategories();
        List<CategoryDto> categoryDtos = categories.stream().map(CategoryDto::new).toList();
        return ResponseEntity.ok(categoryDtos);
    }
}
