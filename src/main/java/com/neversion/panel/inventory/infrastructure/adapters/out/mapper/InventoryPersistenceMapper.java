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
                .price(entity.getPrice())
                .duration(entity.getDuration())
                .accountType(entity.getAccountType())
                .stock(entity.getStock())
                .build();
            }
            
    public InventoryEntity toEntity(Inventory domain) {
        if (domain == null)
            return null;
        return InventoryEntity.builder()
                .id(domain.getId())
                .price(domain.getPrice())
                .duration(domain.getDuration())
                .accountType(domain.getAccountType())
                .stock(domain.getStock())
                .build();
    }
}
