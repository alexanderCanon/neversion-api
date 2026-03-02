package com.neversion.panel.inventory.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.out.InventoryEntity;

@Component
public class InventoryPersistenceMapper {

    public Inventory toDomain(InventoryEntity entity) {
        if (entity == null)
            return null;
        return Inventory.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .price(entity.getPrice())
                .durationDays(entity.getDurationDays())
                .accountType(entity.getAccountType())
                .stock(entity.getStock())
                .build();
    }

    public InventoryEntity toEntity(Inventory domain) {
        if (domain == null)
            return null;
        return InventoryEntity.builder()
                .id(domain.getId())
                .productId(domain.getProductId())
                .price(domain.getPrice())
                .durationDays(domain.getDurationDays())
                .accountType(domain.getAccountType())
                .stock(domain.getStock())
                .build();
    }
}
