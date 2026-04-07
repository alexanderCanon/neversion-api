package com.neversion.api.product.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.product.application.port.in.UpdateProductUseCase;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.port.out.ProductRepositoryPort;

@Service
public class UpdateProductService implements UpdateProductUseCase {

    private static final int MIN_NAME_LENGTH = 3;

    private final ProductRepositoryPort productRepositoryPort;

    public UpdateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public Product update(UUID id, Product product) {
        Product existingProduct = productRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        validateName(product.getName());
        checkDuplicateName(id, product.getName());

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setCategory(product.getCategory());

        return productRepositoryPort.save(existingProduct);
    }

    private void validateName(String name) {
        if (name == null || name.length() < MIN_NAME_LENGTH) {
            throw new BusinessRuleException("Product name must be at least " + MIN_NAME_LENGTH + " characters");
        }
    }

    private void checkDuplicateName(UUID currentId, String name) {
        productRepositoryPort.findByName(name).ifPresent(existing -> {
            if (currentId == null || !existing.getId().equals(currentId)) {
                throw new BusinessRuleException("Product with name '" + name + "' already exists");
            }
        });
    }
}
