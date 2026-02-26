package com.neversion.panel.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.application.port.in.UpdateInventoryStockUseCase;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

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
