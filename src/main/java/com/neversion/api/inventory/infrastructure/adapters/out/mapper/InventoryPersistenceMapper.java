package com.neversion.api.inventory.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.infrastructure.adapters.out.InventoryEntity;

@Component
public class InventoryPersistenceMapper {

    public Inventory toDomain(InventoryEntity entity) {
        return entity != null ? Inventory.builder()
                .id(entity.getId())
                .productId(entity.getProductId())
                .price(entity.getPrice())
                .durationDays(entity.getDurationDays())
                .accountType(entity.getAccountType())
                .stock(entity.getStock())
                .maxProfiles(entity.getMaxProfiles())
                .build() : null;
    }

    public InventoryEntity toEntity(Inventory domain) {
        return domain != null ? InventoryEntity.builder()
                .id(domain.getId())
                .productId(domain.getProductId())
                .price(domain.getPrice())
                .durationDays(domain.getDurationDays())
                .accountType(domain.getAccountType())
                .stock(domain.getStock())
                .maxProfiles(domain.getMaxProfiles())
                .build() : null;
    }
}
