package com.neversion.panel.account.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.shared.domain.model.enums.AccountType;

public interface AccountRepositoryAdapter extends JpaRepository<AccountEntity, UUID> {
    List<AccountEntity> findBySeller(String seller);

    List<AccountEntity> findByAccountType(AccountType accountType);

    List<AccountEntity> findByExpirationDateBefore(LocalDate date);

    List<AccountEntity> findByIsActive(Boolean isActive);

    @Modifying
    @Transactional
    @Query("UPDATE AccountEntity a SET a.isActive = false WHERE a.id = :id")
    void deactivate(@Param("id") UUID id);
}
