package com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;

@Component
public class InventoryMapper {

    public Inventory toDomain(InventoryRequest request) {
        return new Inventory(
            null,
            request.getCredentialsId(),
            null,
            null,
            request.getSeller(),
            request.getPriceSeller(),
            request.getStock() != null ? request.getStock() : 1,
            request.getAccountType(),
            request.getExpirationDate(),
            true,
            null
        );
    }

    public InventoryResponse toResponse(Inventory inventory) {
        return new InventoryResponse(
            inventory.id(),
            inventory.credentialEmail(),
            inventory.credentialPass(),
            inventory.seller(),
            inventory.priceSeller(),
            inventory.accountType(),
            inventory.expirationDate(),
            inventory.isActive()
        );
    }
}
