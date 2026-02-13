package com.neversion.panel.inventory.application.port.in;

import java.time.LocalDate;
import java.util.List;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface GetInventoryUseCase {
    Inventory getById(Long id);
    List<Inventory> getBySeller(String seller);
    List<Inventory> getByAccountType(AccountType accountType);
    List<Inventory> getByExpirationDateBefore(LocalDate date);
    List<Inventory> getByIsActive(Boolean isActive);
    List<Inventory> getAll();
}
