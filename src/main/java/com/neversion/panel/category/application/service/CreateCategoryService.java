package com.neversion.panel.category.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.category.application.port.in.CreateCategoryUseCase;
import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.domain.port.out.CategoryRepositoryPort;

@Service
public class CreateCategoryService implements CreateCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    public CreateCategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public Category create(Category category) {
        return categoryRepositoryPort.save(category);
    }
}
