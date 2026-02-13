package com.neversion.panel.inventory.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.infrastructure.adapters.out.InventoryEntity;

@Component
public class InventoryPersistenceMapper {

    public Inventory toDomain(InventoryEntity entity) {
        return new Inventory(
            entity.getId(),
            entity.getCredentialsId(),
            entity.getCredential().getEmail(),
            entity.getCredential().getPass(),
            entity.getSeller(),
            entity.getPriceSeller(),
            entity.getStock(),
            entity.getAccountType(),
            entity.getExpirationDate(),
            entity.getIsActive(),
            entity.getCreatedAt()
        );
    }

    public InventoryEntity toEntity(Inventory inventory) {
        return new InventoryEntity(
            inventory.id(),
            inventory.credentialsId(),
            inventory.seller(),
            inventory.priceSeller(),
            inventory.stock(),
            inventory.accountType(),
            inventory.expirationDate(),
            inventory.isActive()
        );
    }
}
