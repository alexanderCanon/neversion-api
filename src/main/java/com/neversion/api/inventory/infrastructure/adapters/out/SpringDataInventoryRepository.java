package com.neversion.api.inventory.infrastructure.adapters.out;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.api.shared.domain.model.enums.AccountType;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByAccountType(AccountType accountType);

    List<InventoryEntity> findByProductId(UUID productId);

    boolean existsByProductId(UUID productId);
}
