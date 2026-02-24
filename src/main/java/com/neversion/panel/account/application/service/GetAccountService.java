package com.neversion.panel.account.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.account.application.port.in.GetAccountUseCase;
import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.model.enums.AccountType;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.exception.ResourceNotFoundException;

@Service
public class GetAccountService implements GetAccountUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    public GetAccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public Account getById(Long id) {
        return accountRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
    }

    @Override
    public List<Account> getBySeller(String seller) {
        return accountRepositoryPort.findBySeller(seller);
    }

    @Override
    public List<Account> getByAccountType(AccountType accountType) {
        return accountRepositoryPort.findByAccountType(accountType);
    }

    @Override
    public List<Account> getByExpirationDateBefore(LocalDate date) {
        return accountRepositoryPort.findByExpirationDateBefore(date);
    }

    @Override
    public List<Account> getByIsActive(Boolean isActive) {
        return accountRepositoryPort.findByIsActive(isActive);
    }

    @Override
    public List<Account> getAll() {
        return accountRepositoryPort.findAll();
    }
}
