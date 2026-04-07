package com.neversion.api.product.application.port.in;

import java.util.UUID;

import com.neversion.api.product.domain.model.Product;

public interface UpdateProductUseCase {
    Product update(UUID id, Product product);
}
