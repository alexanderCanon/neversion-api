package com.neversion.panel.category.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.category.application.port.in.CreateCategoryUseCase;
import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.infrastructure.adapters.in.rest.dto.CategoryRequest;
import com.neversion.panel.category.infrastructure.adapters.in.rest.dto.CategoryResponse;
import com.neversion.panel.category.infrastructure.adapters.in.rest.mapper.CategoryMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryPostController {
    private final CreateCategoryUseCase createCategoryUseCase;
    private final CategoryMapper categoryMapper;

    public CategoryPostController(CreateCategoryUseCase createCategoryUseCase, CategoryMapper categoryMapper) {
        this.createCategoryUseCase = createCategoryUseCase;
        this.categoryMapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryMapper.toDomain(request);
        Category created = createCategoryUseCase.create(category);
        CategoryResponse response = categoryMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
