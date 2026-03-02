package com.neversion.panel.product.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.product.application.port.in.GetProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@Service
public class GetProductService implements GetProductUseCase {

    private final ProductRepositoryPort productRepositoryPort;

    public GetProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Product getById(UUID id) {
        Product product = productRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        return product;
    }

    @Override
    public Product getByName(String name) {
        Product product = productRepositoryPort.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("Product with name " + name + " not found"));
        return product;
    }

    @Override
    public List<Product> getAll() {
        List<Product> products = productRepositoryPort.findAll();
        return products;
    }

    @Override
    public List<Product> getByCategory(CategoryType category) {
        return productRepositoryPort.findByCategory(category);
    }

}
