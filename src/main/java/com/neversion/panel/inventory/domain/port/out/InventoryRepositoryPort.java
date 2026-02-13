package com.neversion.panel.inventory.domain.port.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);
    Optional<Inventory> findById(Long id);
    List<Inventory> findBySeller(String seller);
    List<Inventory> findByAccountType(AccountType accountType);
    List<Inventory> findByExpirationDateBefore(LocalDate date);
    List<Inventory> findByIsActive(Boolean isActive);
    List<Inventory> findAll();
    void deactivate(Long id);
}
