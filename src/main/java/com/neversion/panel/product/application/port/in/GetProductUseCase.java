package com.neversion.panel.product.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;

public interface GetProductUseCase {
    Product getById(UUID id);

    Product getByName(String name);

    List<Product> getAll();

    List<Product> getByCategory(CategoryType category);
}
