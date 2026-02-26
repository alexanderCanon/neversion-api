package com.neversion.panel.inventory.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;

@Component
public class InventoryMapper {

    public Inventory toDomain(InventoryRequest request) {
        if (request == null)
            return null; //ese null podria lanzar un NullPointerException

        return Inventory.builder()
                .price(request.priceAmount())
                .duration(request.duration())
                .accountType(AccountType.valueOf(request.accountType().toUpperCase()))
                .stock(request.stock())
                .build();
    }

    public InventoryResponse toResponse(Inventory inventory) {
        if (inventory == null)
            return null;

        return new InventoryResponse(
                inventory.getProduct().getId(),
                inventory.getPrice(),
                inventory.getDuration(),
                inventory.getAccountType(),
                inventory.getStock());
    }
}
