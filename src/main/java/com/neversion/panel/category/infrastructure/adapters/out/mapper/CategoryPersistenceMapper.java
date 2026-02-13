package com.neversion.panel.category.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.infrastructure.adapters.out.CategoryEntity;

@Component
public class CategoryPersistenceMapper {

    public Category toDomain(CategoryEntity entity) {
        return new Category(
            entity.getId(),
            entity.getName(),
            entity.getDescription()
        );
    }

    public CategoryEntity toEntity(Category category) {
        return new CategoryEntity(
            category.id(),
            category.name(),
            category.description()
        );
    }
}
