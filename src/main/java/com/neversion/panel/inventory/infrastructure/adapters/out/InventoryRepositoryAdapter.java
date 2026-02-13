package com.neversion.panel.inventory.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.inventory.domain.model.enums.AccountType;

public interface InventoryRepositoryAdapter extends JpaRepository<InventoryEntity, Long> {
    List<InventoryEntity> findBySeller(String seller);
    List<InventoryEntity> findByAccountType(AccountType accountType);
    List<InventoryEntity> findByExpirationDateBefore(LocalDate date);
    List<InventoryEntity> findByIsActive(Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE InventoryEntity i SET i.isActive = false WHERE i.id = :id")
    void deactivate(@Param("id") Long id);
}
