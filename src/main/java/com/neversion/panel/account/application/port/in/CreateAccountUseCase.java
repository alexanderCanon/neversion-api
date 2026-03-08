package com.neversion.panel.account.application.port.in;

import com.neversion.panel.account.domain.model.Account;

public interface CreateAccountUseCase {
    Account create(Account account);
}
