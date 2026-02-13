package com.neversion.panel.inventory.application.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.inventory.application.port.in.CreateInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class CreateInventoryService implements CreateInventoryUseCase {
    private final InventoryRepositoryPort inventoryRepositoryPort;

    public CreateInventoryService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    public Inventory create(Inventory inventory) {
        if (inventory.expirationDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new BusinessRuleException("Expiration date must be at least 15 days from now");
        }
        return inventoryRepositoryPort.save(inventory);
    }
}
