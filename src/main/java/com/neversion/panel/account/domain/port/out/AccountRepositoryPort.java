package com.neversion.panel.account.domain.port.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.shared.domain.model.enums.AccountType;

public interface AccountRepositoryPort {
    Account save(Account account);

    Optional<Account> findById(UUID id);

    List<Account> findBySeller(String seller);

    List<Account> findByAccountType(AccountType accountType);

    List<Account> findByExpirationDateBefore(LocalDate date);

    List<Account> findByIsActive(Boolean isActive);

    List<Account> findAll();

    void deactivate(UUID id);
}
