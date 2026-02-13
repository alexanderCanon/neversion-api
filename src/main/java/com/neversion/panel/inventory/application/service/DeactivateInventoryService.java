package com.neversion.panel.inventory.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.application.port.in.DeactivateInventoryUseCase;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class DeactivateInventoryService implements DeactivateInventoryUseCase {
    private final InventoryRepositoryPort inventoryRepositoryPort;

    public DeactivateInventoryService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    public void deactivate(Long id) {
        inventoryRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));
        inventoryRepositoryPort.deactivate(id);
    }
}
