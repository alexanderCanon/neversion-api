package com.neversion.panel.account.application.port.in;

import java.time.LocalDate;
import java.util.List;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.model.enums.AccountType;

public interface GetAccountUseCase {
    Account getById(Long id);

    List<Account> getBySeller(String seller);

    List<Account> getByAccountType(AccountType accountType);

    List<Account> getByExpirationDateBefore(LocalDate date);

    List<Account> getByIsActive(Boolean isActive);

    List<Account> getAll();
}
