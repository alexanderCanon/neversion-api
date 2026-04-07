package com.neversion.api.inventory.application.port.in;

import java.util.UUID;

import com.neversion.api.inventory.domain.model.Inventory;

public interface AddInventoryUseCase {
    Inventory add(UUID productId, Inventory productDetail);
}
