package com.neversion.api.inventory.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.application.port.in.UpdateInventoryPriceUseCase;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;

@Service
public class UpdateInventoryPriceService implements UpdateInventoryPriceUseCase {

    private final InventoryRepositoryPort inventoryRepositoryPort;

    public UpdateInventoryPriceService(InventoryRepositoryPort inventoryRepositoryPort) {
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    @Override
    @Transactional
    public void updatePrice(Long id, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleException("Price cannot be negative");
        }

        var inventory = inventoryRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory with id " + id + " not found"));

        inventory.setPrice(newPrice);
        inventoryRepositoryPort.save(inventory);
    }
}
