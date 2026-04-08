package com.neversion.api.inventory.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.shared.domain.model.enums.AccountType;

public interface GetInventoryUseCase {
    List<Inventory> getAll();

    List<Inventory> getByProductId(UUID productId);

    List<Inventory> getByAccountType(AccountType accountType);

    Inventory getById(Long id);
}
