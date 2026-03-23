package com.neversion.api.inventory.infrastructure.adapters.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.mapper.InventoryMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory", description = "Inventory variant management (product pricing plans)")
public class InventoryPostController {

    private final AddInventoryUseCase addInventoryUseCase;
    private final InventoryMapper inventoryMapper;

    public InventoryPostController(AddInventoryUseCase addInventoryUseCase, InventoryMapper inventoryMapper) {
        this.addInventoryUseCase = addInventoryUseCase;
        this.inventoryMapper = inventoryMapper;
    }

    @PostMapping
    @Operation(summary = "Add inventory variant", description = "Create a new inventory variant (pricing plan) for a product")
    @ApiResponse(responseCode = "201", description = "Inventory variant created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request or missing product ID")
    @ApiResponse(responseCode = "404", description = "Product not found")
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest request) {
        if (request.productId() == null) {
            throw new BusinessRuleException(
                    "Product ID is required to add an inventory variant.");
        }

        Inventory inventory = inventoryMapper.toDomain(request);
        Inventory createdInventory = addInventoryUseCase.add(request.productId(), inventory);
        InventoryResponse response = inventoryMapper.toResponse(createdInventory);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
