package com.neversion.panel.inventory.application.port.in;

import java.util.List;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface GetInventoryUseCase {
    List<Inventory> getAll();
    List<Inventory> getByProductId(Long productId);
    List<Inventory> getByAccountType(AccountType accountType);
}
