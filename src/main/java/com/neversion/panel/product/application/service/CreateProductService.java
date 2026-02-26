package com.neversion.panel.product.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.product.application.port.in.CreateProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@Service
public class CreateProductService implements CreateProductUseCase {

    private static final int MIN_NAME_LENGTH = 3;

    private final ProductRepositoryPort productRepositoryPort;

    public CreateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    @Transactional
    public Product create(Product product) {
        validateName(product.getName());
        checkDuplicateName(null, product.getName());
        return productRepositoryPort.save(product);
    }

    private void validateName(String name) {
        if (name == null || name.length() < MIN_NAME_LENGTH) {
            throw new BusinessRuleException("Product name must be at least " + MIN_NAME_LENGTH + " characters");
        }
    }

    private void checkDuplicateName(Integer currentId, String name) {
        if (productRepositoryPort.existsByName(name)) {
            throw new BusinessRuleException("Product with name '" + name + "' already exists");
        }
    }
}
