package com.neversion.panel.category.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.category.application.port.in.GetCategoryUseCase;
import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.domain.port.out.CategoryRepositoryPort;

@Service
public class GetCategoryService implements GetCategoryUseCase {
    private final CategoryRepositoryPort categoryRepositoryPort;

    public GetCategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public Category getById(Integer id) {
        return categoryRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    @Override
    public Category getByName(String name) {
        return categoryRepositoryPort.findByName(name)
            .orElseThrow(() -> new ResourceNotFoundException("Category with name " + name + " not found"));
    }

    @Override
    public List<Category> getAll() {
        return categoryRepositoryPort.findAll();
    }
}
