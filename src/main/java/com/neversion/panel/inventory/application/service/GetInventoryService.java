package com.neversion.panel.inventory.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

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
    public List<Inventory> getByProductId(Long productId) {
        return inventoryRepositoryPort.findByProductId(productId);
    }

    @Override
    public List<Inventory> getByAccountType(AccountType accountType) {
        return inventoryRepositoryPort.findByAccountType(accountType);
    }
}
