package com.neversion.api.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.application.port.in.DecreaseStockUseCase;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class DecreaseStockService implements DecreaseStockUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public DecreaseStockService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void decrease(Long id, Integer quantity) {
        var inventory = inventoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));

        int newStock = inventory.getStock() - quantity;
        if (newStock < 0) {
            throw new BusinessRuleException("Insufficient stock. Current stock: " + inventory.getStock());
        }

        inventory.setStock(newStock);
        inventoryRepositoryPort.save(inventory);
    }
}
