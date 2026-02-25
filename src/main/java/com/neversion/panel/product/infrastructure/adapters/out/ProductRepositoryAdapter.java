package com.neversion.panel.product.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.panel.product.domain.model.enums.CategoryType;

public interface ProductRepositoryAdapter extends JpaRepository<ProductEntity, Integer> {
    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByCategory(CategoryType category);

}
