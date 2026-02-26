package com.neversion.panel.inventory.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);

    List<Inventory> findAll();

    List<Inventory> findByAccountType(AccountType accountType);

    List<Inventory> findByProductId(Long productId);

    boolean existsByProductId(Long productId);

    Optional<Inventory> findById(Long id);

    void deleteById(Long id);
}
