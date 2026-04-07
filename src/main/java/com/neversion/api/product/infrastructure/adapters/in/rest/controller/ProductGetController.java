package com.neversion.api.product.infrastructure.adapters.in.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.product.application.port.in.GetProductUseCase;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.model.enums.CategoryType;
import com.neversion.api.product.infrastructure.adapters.in.rest.dto.ProductResponse;
import com.neversion.api.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product catalog management")
public class ProductGetController {

    private final GetProductUseCase getProductUseCase;
    private final ProductMapper productMapper;

    public ProductGetController(GetProductUseCase getProductUseCase, ProductMapper productMapper) {
        this.getProductUseCase = getProductUseCase;
        this.productMapper = productMapper;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a single product by its UUID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        Product product = getProductUseCase.getById(id);
        ProductResponse response = productMapper.toResponse(product);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get products", description = "Retrieve products filtered by name, category, or all")
    @ApiResponse(responseCode = "200", description = "Products retrieved successfully")
    public ResponseEntity<?> getProducts(
            @Parameter(description = "Filter by exact product name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by category (STREAMING, SOFTWARE, GIFT_CARD, RECHARGE, DIGITAL_SERVICE)") @RequestParam(required = false) String category) {
        if (name != null && !name.isBlank()) {
            Product product = getProductUseCase.getByName(name);
            ProductResponse response = productMapper.toResponse(product);
            return ResponseEntity.ok(response);
        }
        if (category != null && !category.isBlank()) {
            CategoryType categoryType = CategoryType.valueOf(category.toUpperCase());
            List<ProductResponse> response = getProductUseCase.getByCategory(categoryType).stream()
                    .map(productMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }
        List<ProductResponse> response = getProductUseCase.getAll().stream()
                .map(productMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
