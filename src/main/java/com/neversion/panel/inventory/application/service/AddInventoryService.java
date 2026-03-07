package com.neversion.panel.inventory.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.inventory.application.port.in.AddInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.inventory.domain.service.InventoryPricingService;
import com.neversion.panel.product.application.port.in.GetProductUseCase;

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
