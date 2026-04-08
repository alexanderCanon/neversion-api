package com.neversion.api.inventory.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.dto.InventoryRequest;
import com.neversion.api.inventory.infrastructure.adapters.in.rest.dto.InventoryResponse;
import com.neversion.api.shared.domain.model.enums.AccountType;

@Component
public class InventoryMapper {

    public Inventory toDomain(InventoryRequest request) {
        return request != null ? Inventory.builder()
                .price(request.priceAmount())
                .durationDays(request.durationDays())
                .accountType(AccountType.valueOf(request.accountType().toUpperCase()))
                .stock(request.stock())
                .maxProfiles(request.maxProfiles())
                .build() : null;
    }

    public InventoryResponse toResponse(Inventory inventory) {
        return inventory != null ? InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .price(inventory.getPrice())
                .durationDays(inventory.getDurationDays())
                .accountType(inventory.getAccountType().name())
                .stock(inventory.getStock())
                .maxProfiles(inventory.getMaxProfiles())
                .build() : null;
    }
}
