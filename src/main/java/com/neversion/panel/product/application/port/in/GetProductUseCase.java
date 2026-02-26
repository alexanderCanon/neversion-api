package com.neversion.panel.product.application.port.in;

import java.util.List;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;

public interface GetProductUseCase {
    Product getById(Long id);
    Product getByName(String name);
    List<Product> getAll();
    List<Product> getByCategory(CategoryType category);
}
