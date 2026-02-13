package com.neversion.panel.credential.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.credential.application.port.in.GetCredentialUseCase;
import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.domain.port.out.CredentialRepositoryPort;

@Service
public class GetCredentialService implements GetCredentialUseCase {
    private final CredentialRepositoryPort credentialRepositoryPort;

    public GetCredentialService(CredentialRepositoryPort credentialRepositoryPort) {
        this.credentialRepositoryPort = credentialRepositoryPort;
    }

    @Override
    public Credential getById(Long id) {
        return credentialRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Credential with id " + id + " not found"));
    }

    @Override
    public List<Credential> getByEmail(String email) {
        return credentialRepositoryPort.findByEmail(email);
    }

    @Override
    public List<Credential> getByIsActive(Boolean isActive) {
        return credentialRepositoryPort.findByIsActive(isActive);
    }

    @Override
    public List<Credential> getAll() {
        return credentialRepositoryPort.findAll();
    }
}
