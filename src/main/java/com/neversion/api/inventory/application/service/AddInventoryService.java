package com.neversion.api.inventory.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.inventory.domain.service.InventoryPricingService;
import com.neversion.api.product.application.port.in.GetProductUseCase;

@Service
public class AddInventoryService implements AddInventoryUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;
    private final GetProductUseCase getProductUseCase;
    private final InventoryPricingService inventoryPricingService;

    public AddInventoryService(
            InventoryRepositoryPort inventoryRepositoryPort,
            GetProductUseCase getProductUseCase,
            InventoryPricingService inventoryPricingService) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
        this.getProductUseCase = getProductUseCase;
        this.inventoryPricingService = inventoryPricingService;
    }

    @Override
    public Inventory add(UUID productId, Inventory inventory) {
        // Validate that the product exists
        getProductUseCase.getById(productId);
        inventory.setProductId(productId);

        // Delegate pricing logic to domain service (BR-04)
        inventoryPricingService.applyDurationDiscount(inventory);

        return inventoryRepositoryPort.save(inventory);
    }
}
