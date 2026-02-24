package com.neversion.panel.account.domain.port.out;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.model.enums.AccountType;

public interface AccountRepositoryPort {
    Account save(Account account);

    Optional<Account> findById(Long id);

    List<Account> findBySeller(String seller);

    List<Account> findByAccountType(AccountType accountType);

    List<Account> findByExpirationDateBefore(LocalDate date);

    List<Account> findByIsActive(Boolean isActive);

    List<Account> findAll();

    void deactivate(Long id);
}
