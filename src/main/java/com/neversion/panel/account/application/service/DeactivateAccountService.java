package com.neversion.panel.account.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.account.application.port.in.DeactivateAccountUseCase;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.exception.ResourceNotFoundException;

@Service
public class DeactivateAccountService implements DeactivateAccountUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    public DeactivateAccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public void deactivate(Long id) {
        accountRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
        accountRepositoryPort.deactivate(id);
    }
}
