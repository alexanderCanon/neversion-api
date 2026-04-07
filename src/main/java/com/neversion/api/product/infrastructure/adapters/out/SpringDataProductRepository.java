package com.neversion.api.product.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.api.product.domain.model.enums.CategoryType;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, UUID> {
    Optional<ProductEntity> findByName(String name);

    List<ProductEntity> findByCategory(CategoryType category);

    boolean existsByName(String name);
}
