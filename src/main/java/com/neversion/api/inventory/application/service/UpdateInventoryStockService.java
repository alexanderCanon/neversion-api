package com.neversion.api.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.application.port.in.UpdateInventoryStockUseCase;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class UpdateInventoryStockService implements UpdateInventoryStockUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public UpdateInventoryStockService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void updateStock(Long id, Integer newStock) {
        if (newStock < 0) {
            throw new BusinessRuleException("Stock cannot be negative");
        }

        var inventory = inventoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));

        inventory.setStock(newStock);
        inventoryRepositoryPort.save(inventory);
    }
}
