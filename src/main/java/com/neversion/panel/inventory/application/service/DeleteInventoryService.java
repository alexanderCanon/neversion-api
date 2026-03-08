package com.neversion.panel.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.application.port.in.DeleteInventoryUseCase;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class DeleteInventoryService implements DeleteInventoryUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public DeleteInventoryService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!inventoryRepositoryPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Inventory with id " + id + " not found");
        }
        inventoryRepositoryPort.deleteById(id);
    }
}
