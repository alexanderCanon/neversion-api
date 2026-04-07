package com.neversion.api.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.application.port.in.IncreaseStockUseCase;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class IncreaseStockService implements IncreaseStockUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public IncreaseStockService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void increase(Long id, Integer quantity) {
        if (quantity < 0) {
            throw new BusinessRuleException("Quantity must be positive");
        }

        var inventory = inventoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));

        inventory.setStock(inventory.getStock() + quantity);
        inventoryRepositoryPort.save(inventory);
    }
}
