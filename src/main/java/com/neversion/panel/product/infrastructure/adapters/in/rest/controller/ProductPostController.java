package com.neversion.panel.product.infrastructure.adapters.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.product.application.port.in.CreateProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductRequest;
import com.neversion.panel.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductPostController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper productMapper;

    public ProductPostController(CreateProductUseCase createProductUseCase, ProductMapper productMapper) {
        this.createProductUseCase = createProductUseCase;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productMapper.toDomain(request);
        Product createdProduct = createProductUseCase.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}
