package com.neversion.api.product.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.product.application.port.in.GetProductUseCase;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.model.enums.CategoryType;
import com.neversion.api.product.domain.port.out.ProductRepositoryPort;

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
