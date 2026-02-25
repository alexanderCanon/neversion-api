package com.neversion.panel.product.application.port.in;

import java.util.List;

import com.neversion.panel.product.domain.model.Product;

public interface GetProductUseCase {
    Product getById(Integer id);
    Product getByName(String name);
    List<Product> getAll();
}
