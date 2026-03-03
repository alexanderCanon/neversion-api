package com.neversion.panel.product.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.product.application.port.in.UpdateProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductRequest;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductResponse;
import com.neversion.panel.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductPutController {

    private final UpdateProductUseCase updateProductUseCase;
    private final ProductMapper productMapper;

    public ProductPutController(UpdateProductUseCase updateProductUseCase, ProductMapper productMapper) {
        this.updateProductUseCase = updateProductUseCase;
        this.productMapper = productMapper;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a product", description = "Update an existing product by its UUID")
    @ApiResponse(responseCode = "200", description = "Product updated successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
            @Valid @RequestBody ProductRequest request) {
        Product product = productMapper.toDomain(request);
        Product updatedProduct = updateProductUseCase.update(id, product);
        ProductResponse response = productMapper.toResponse(updatedProduct);
        return ResponseEntity.ok(response);
    }
}
