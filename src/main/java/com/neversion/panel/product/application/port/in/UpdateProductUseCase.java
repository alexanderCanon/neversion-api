package com.neversion.panel.product.application.port.in;

import java.util.UUID;

import com.neversion.panel.product.domain.model.Product;

public interface UpdateProductUseCase {
    Product update(UUID id, Product product);
}
