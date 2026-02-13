package com.neversion.panel.credential.domain.port.out;

import java.util.List;
import java.util.Optional;

import com.neversion.panel.credential.domain.model.Credential;

public interface CredentialRepositoryPort {
    Credential save(Credential credential);
    Optional<Credential> findById(Long id);
    List<Credential> findByEmail(String email);
    List<Credential> findByIsActive(Boolean isActive);
    List<Credential> findAll();
    void deactivate(Long id);
}
