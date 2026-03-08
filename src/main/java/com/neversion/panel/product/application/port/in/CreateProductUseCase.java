package com.neversion.panel.product.application.port.in;

import com.neversion.panel.product.domain.model.Product;

public interface CreateProductUseCase {
    Product create(Product product);
}
