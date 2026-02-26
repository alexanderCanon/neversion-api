package com.neversion.panel.product.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.panel.product.domain.model.enums.CategoryType;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    List<ProductEntity> findByCategory(CategoryType category);
    boolean existsByName(String name);
}
