package com.neversion.panel.credential.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.credential.application.port.in.CreateCredentialUseCase;
import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.domain.port.out.CredentialRepositoryPort;

@Service
public class CreateCredentialService implements CreateCredentialUseCase {
    private final CredentialRepositoryPort credentialRepositoryPort;

    public CreateCredentialService(CredentialRepositoryPort credentialRepositoryPort) {
        this.credentialRepositoryPort = credentialRepositoryPort;
    }

    @Override
    public Credential create(Credential credential) {
        return credentialRepositoryPort.save(credential);
    }
}
