package com.neversion.panel.product.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;

public interface ProductRepositoryPort {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(Long id);
    Optional<Product> findByName(String name);
    List<Product> findByCategory(CategoryType category);
    void deleteById(Long id);
    boolean existsByName(String name);
}
