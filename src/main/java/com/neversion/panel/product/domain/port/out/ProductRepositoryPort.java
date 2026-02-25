package com.neversion.panel.product.domain.port.out;

import java.util.Optional;

import java.util.List;
import com.neversion.panel.product.domain.model.Product;

public interface ProductRepositoryPort {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(Integer id);
    Optional<Product> findByName(String name);
}
