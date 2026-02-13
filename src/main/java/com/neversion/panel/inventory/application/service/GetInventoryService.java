package com.neversion.panel.inventory.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
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
    public Inventory getById(Long id) {
        return inventoryRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));
    }

    @Override
    public List<Inventory> getBySeller(String seller) {
        return inventoryRepositoryPort.findBySeller(seller);
    }

    @Override
    public List<Inventory> getByAccountType(AccountType accountType) {
        return inventoryRepositoryPort.findByAccountType(accountType);
    }

    @Override
    public List<Inventory> getByExpirationDateBefore(LocalDate date) {
        return inventoryRepositoryPort.findByExpirationDateBefore(date);
    }

    @Override
    public List<Inventory> getByIsActive(Boolean isActive) {
        return inventoryRepositoryPort.findByIsActive(isActive);
    }

    @Override
    public List<Inventory> getAll() {
        return inventoryRepositoryPort.findAll();
    }
}
