package com.neversion.panel.category.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.category.domain.model.Category;

public interface CategoryRepositoryPort {
    Category save(Category category);
    Optional<Category> findById(Integer id);
    Optional<Category> findByName(String name);
    List<Category> findAll();
}
