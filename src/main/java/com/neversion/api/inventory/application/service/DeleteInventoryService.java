package com.neversion.api.inventory.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.application.port.in.DeleteInventoryUseCase;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

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
