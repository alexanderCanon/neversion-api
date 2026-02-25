package com.neversion.panel.product.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.product.application.port.in.CreateProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@Service
public class CreateProductService implements CreateProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public CreateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public Product create(Product product) {
        return productRepositoryPort.save(product);
    }
}
