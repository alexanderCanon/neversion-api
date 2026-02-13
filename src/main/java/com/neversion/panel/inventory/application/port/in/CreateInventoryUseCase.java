package com.neversion.panel.inventory.application.port.in;

import com.neversion.panel.inventory.domain.model.Inventory;

public interface CreateInventoryUseCase {
    Inventory create(Inventory inventory);
}
