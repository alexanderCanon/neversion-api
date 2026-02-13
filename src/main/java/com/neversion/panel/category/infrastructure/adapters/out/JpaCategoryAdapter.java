package com.neversion.panel.category.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.category.domain.model.Category;
import com.neversion.panel.category.domain.port.out.CategoryRepositoryPort;
import com.neversion.panel.category.infrastructure.adapters.out.mapper.CategoryPersistenceMapper;

@Repository
public class JpaCategoryAdapter implements CategoryRepositoryPort {
    private final CategoryRepositoryAdapter categoryRepositoryAdapter;
    private final CategoryPersistenceMapper categoryPersistenceMapper;

    public JpaCategoryAdapter(CategoryRepositoryAdapter categoryRepositoryAdapter,
        CategoryPersistenceMapper categoryPersistenceMapper) {
        this.categoryRepositoryAdapter = categoryRepositoryAdapter;
        this.categoryPersistenceMapper = categoryPersistenceMapper;
    }

    @Override
    public Category save(Category category) {
        CategoryEntity entity = categoryPersistenceMapper.toEntity(category);
        CategoryEntity saved = categoryRepositoryAdapter.save(entity);
        return categoryPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepositoryAdapter.findById(id)
            .map(categoryPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepositoryAdapter.findByName(name)
            .map(categoryPersistenceMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepositoryAdapter.findAll()
            .stream()
            .map(categoryPersistenceMapper::toDomain)
            .toList();
    }
}
