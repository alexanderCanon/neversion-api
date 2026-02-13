package com.neversion.panel.credential.application.service;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.credential.application.port.in.DeactivateCredentialUseCase;
import com.neversion.panel.credential.domain.port.out.CredentialRepositoryPort;

@Service
public class DeactivateCredentialService implements DeactivateCredentialUseCase {
    private final CredentialRepositoryPort credentialRepositoryPort;

    public DeactivateCredentialService(CredentialRepositoryPort credentialRepositoryPort) {
        this.credentialRepositoryPort = credentialRepositoryPort;
    }

    @Override
    public void deactivate(Long id) {
        credentialRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Credential with id " + id + " not found"));
        credentialRepositoryPort.deactivate(id);
    }
}
