package com.neversion.panel.inventory.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.shared.domain.model.enums.AccountType;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);

    List<Inventory> findAll();

    List<Inventory> findByAccountType(AccountType accountType);

    List<Inventory> findByProductId(UUID productId);

    boolean existsByProductId(UUID productId);

    Optional<Inventory> findById(Long id);

    void deleteById(Long id);
}
