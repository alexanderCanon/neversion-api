package com.neversion.api.product.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.product.application.port.in.DeleteProductUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products")
public class ProductDeleteController {

    private final DeleteProductUseCase deleteProductUseCase;

    public ProductDeleteController(DeleteProductUseCase deleteProductUseCase) {
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product", description = "Soft-delete a product by its UUID")
    @ApiResponse(responseCode = "204", description = "Product deleted successfully")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        deleteProductUseCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
