package com.neversion.api.account.application.port.in;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.shared.domain.model.enums.AccountType;

public interface GetAccountUseCase {
    Account getById(UUID id);

    List<Account> getBySeller(String seller);

    List<Account> getByAccountType(AccountType accountType);

    List<Account> getByExpirationDateBefore(LocalDate date);

    List<Account> getByIsActive(Boolean isActive);

    List<Account> getAll();
}
