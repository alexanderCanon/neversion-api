package com.neversion.api.product.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.model.enums.CategoryType;

public interface ProductRepositoryPort {
    Product save(Product product);

    List<Product> findAll();

    Optional<Product> findById(UUID id);

    Optional<Product> findByName(String name);

    List<Product> findByCategory(CategoryType category);

    void deleteById(UUID id);

    boolean existsByName(String name);
}
