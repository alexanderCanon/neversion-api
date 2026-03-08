package com.neversion.panel.inventory.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@Service
public class GetInventoryService implements GetInventoryUseCase {
    private final InventoryRepositoryPort inventoryRepositoryPort;

    public GetInventoryService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepositoryPort.findAll();
    }

    @Override
    public List<Inventory> getByProductId(UUID productId) {
        return inventoryRepositoryPort.findByProductId(productId);
    }

    @Override
    public List<Inventory> getByAccountType(AccountType accountType) {
        return inventoryRepositoryPort.findByAccountType(accountType);
    }

    @Override
    public Inventory getById(Long id) {
        return inventoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory item not found with id: " + id));
    }
}
