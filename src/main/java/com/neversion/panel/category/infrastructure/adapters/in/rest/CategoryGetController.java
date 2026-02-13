package com.neversion.panel.category.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.category.application.port.in.GetCategoryUseCase;
import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.infrastructure.adapters.in.rest.dto.CategoryResponse;
import com.neversion.panel.category.infrastructure.adapters.in.rest.mapper.CategoryMapper;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryGetController {

    private final GetCategoryUseCase getCategoryUseCase;
    private final CategoryMapper categoryMapper;

    public CategoryGetController(GetCategoryUseCase getCategoryUseCase, CategoryMapper categoryMapper) {
        this.getCategoryUseCase = getCategoryUseCase;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        Category category = getCategoryUseCase.getById(id);
        CategoryResponse response = categoryMapper.toResponse(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getCategories(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            Category category = getCategoryUseCase.getByName(name);
            CategoryResponse response = categoryMapper.toResponse(category);
            return ResponseEntity.ok(response);
        }
        List<CategoryResponse> response = getCategoryUseCase.getAll().stream()
            .map(categoryMapper::toResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
}
