package com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@Component
public class InventoryMapper {

    public Inventory toDomain(InventoryRequest request) {
        if (request == null)
            return null;

        return Inventory.builder()
                .price(request.priceAmount())
                .durationDays(request.durationDays())
                .accountType(AccountType.valueOf(request.accountType().toUpperCase()))
                .stock(request.stock())
                .build();
    }

    public InventoryResponse toResponse(Inventory inventory) {
        if (inventory == null)
            return null;

        return new InventoryResponse(
                inventory.getProductId(),
                inventory.getPrice(),
                inventory.getDurationDays(),
                inventory.getAccountType(),
                inventory.getStock());
    }
}
