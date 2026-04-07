package com.neversion.api.product.infrastructure.adapters.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.product.application.port.in.CreateProductUseCase;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.infrastructure.adapters.in.rest.dto.ProductRequest;
import com.neversion.api.product.infrastructure.adapters.in.rest.dto.ProductResponse;
import com.neversion.api.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductPostController {

    private final CreateProductUseCase createProductUseCase;
    private final ProductMapper productMapper;

    public ProductPostController(CreateProductUseCase createProductUseCase, ProductMapper productMapper) {
        this.createProductUseCase = createProductUseCase;
        this.productMapper = productMapper;
    }

    @PostMapping
    @Operation(summary = "Create a product", description = "Create a new product in the catalog")
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or business rule violation")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = productMapper.toDomain(request);
        Product created = createProductUseCase.create(product);
        ProductResponse response = productMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
