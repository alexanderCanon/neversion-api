package com.neversion.panel.category.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.infrastructure.adapters.in.rest.dto.CategoryRequest;
import com.neversion.panel.category.infrastructure.adapters.in.rest.dto.CategoryResponse;

@Component
public class CategoryMapper {

    public Category toDomain(CategoryRequest request) {
        return new Category(
            null,
            request.getName(),
            request.getDescription()
        );
    }

    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
            category.name(),
            category.description()
        );
    }
}
