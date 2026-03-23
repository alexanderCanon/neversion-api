package com.neversion.api.inventory.infrastructure.adapters.in.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.mapper.InventoryMapper;
import com.neversion.api.shared.domain.model.enums.AccountType;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Public GET controller for inventory variants (pricing plans).
 * <p>
 * All endpoints are public ({@code permitAll}) as defined in the RBAC matrix
 * (SECURITY.md). They allow customers to explore the catalog and view product
 * details before making a reservation.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/inventory")
@Tag(name = "Inventory", description = "Inventory variant management (product pricing plans)")
public class InventoryGetController {

    private final GetInventoryUseCase getInventoryUseCase;
    private final InventoryMapper inventoryMapper;

    public InventoryGetController(GetInventoryUseCase getInventoryUseCase, InventoryMapper inventoryMapper) {
        this.getInventoryUseCase = getInventoryUseCase;
        this.inventoryMapper = inventoryMapper;
    }

    /**
     * Retrieve a single inventory variant by its ID.
     *
     * @param id the inventory variant ID
     * @return the matching inventory variant
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get inventory variant by ID", description = "Retrieve a single inventory variant (pricing plan) by its numeric ID")
    @ApiResponse(responseCode = "200", description = "Inventory variant found")
    @ApiResponse(responseCode = "404", description = "Inventory variant not found")
    public ResponseEntity<InventoryResponse> getById(
            @Parameter(description = "Inventory variant ID") @PathVariable Long id) {
        Inventory inventory = getInventoryUseCase.getById(id);
        InventoryResponse response = inventoryMapper.toResponse(inventory);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieve inventory variants with optional filters.
     * <ul>
     * <li>No params → returns all variants</li>
     * <li>{@code productId} → filters by product UUID</li>
     * <li>{@code accountType} → filters by account type (FAMILIAR, INDIVIDUAL)</li>
     * </ul>
     *
     * @param productId   optional product UUID filter
     * @param accountType optional account type filter (FAMILIAR, INDIVIDUAL)
     * @return list of matching inventory variants
     */
    @GetMapping
    @Operation(summary = "Get inventory variants", description = "Retrieve all inventory variants, optionally filtered by product ID or account type")
    @ApiResponse(responseCode = "200", description = "Inventory variants retrieved successfully")
    public ResponseEntity<List<InventoryResponse>> getInventory(
            @Parameter(description = "Filter by product UUID") @RequestParam(required = false) UUID productId,
            @Parameter(description = "Filter by account type (FAMILIAR, INDIVIDUAL)") @RequestParam(required = false) String accountType) {

        List<Inventory> results;

        if (productId != null) {
            results = getInventoryUseCase.getByProductId(productId);
        } else if (accountType != null && !accountType.isBlank()) {
            AccountType type = AccountType.valueOf(accountType.toUpperCase());
            results = getInventoryUseCase.getByAccountType(type);
        } else {
            results = getInventoryUseCase.getAll();
        }

        List<InventoryResponse> response = results.stream()
                .map(inventoryMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
