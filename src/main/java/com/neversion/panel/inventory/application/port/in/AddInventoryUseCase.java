package com.neversion.panel.inventory.application.port.in;

import java.util.UUID;

import com.neversion.panel.inventory.domain.model.Inventory;

public interface AddInventoryUseCase {
    Inventory add(UUID productId, Inventory productDetail);
}
