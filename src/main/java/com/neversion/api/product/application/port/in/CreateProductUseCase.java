package com.neversion.api.product.application.port.in;

import com.neversion.api.product.domain.model.Product;

public interface CreateProductUseCase {
    Product create(Product product);
}
