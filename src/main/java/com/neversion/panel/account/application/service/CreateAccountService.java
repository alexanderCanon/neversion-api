package com.neversion.panel.account.application.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.neversion.panel.account.application.port.in.CreateAccountUseCase;
import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.exception.BusinessRuleException;

@Service
public class CreateAccountService implements CreateAccountUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    public CreateAccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Account create(Account account) {
        if (account.expirationDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new BusinessRuleException("Expiration date must be at least 15 days from now");
        }
        return accountRepositoryPort.save(account);
    }
}
