package com.neversion.api.account.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.account.application.port.in.DeactivateAccountUseCase;
import com.neversion.api.account.domain.port.out.AccountRepositoryPort;
import com.neversion.api.exception.ResourceNotFoundException;

@Service
public class DeactivateAccountService implements DeactivateAccountUseCase {
    private final AccountRepositoryPort accountRepositoryPort;

    public DeactivateAccountService(AccountRepositoryPort accountRepositoryPort) {
        this.accountRepositoryPort = accountRepositoryPort;
    }

    @Override
    public void deactivate(UUID id) {
        accountRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account with id " + id + " not found"));
        accountRepositoryPort.deactivate(id);
    }
}
