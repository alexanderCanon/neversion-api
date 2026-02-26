package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findByAccountType(AccountType accountType);
    List<InventoryEntity> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}
