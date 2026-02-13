package com.neversion.panel.credential.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.domain.port.out.CredentialRepositoryPort;
import com.neversion.panel.credential.infrastructure.adapters.out.mapper.CredentialPersistenceMapper;

@Repository
public class JpaCredentialAdapter implements CredentialRepositoryPort {
    private final CredentialRepositoryAdapter credentialRepositoryAdapter;
    private final CredentialPersistenceMapper credentialPersistenceMapper;

    public JpaCredentialAdapter(CredentialRepositoryAdapter credentialRepositoryAdapter,
        CredentialPersistenceMapper credentialPersistenceMapper) {
        this.credentialRepositoryAdapter = credentialRepositoryAdapter;
        this.credentialPersistenceMapper = credentialPersistenceMapper;
    }

    @Override
    public Credential save(Credential credential) {
        CredentialEntity entity = credentialPersistenceMapper.toEntity(credential);
        CredentialEntity saved = credentialRepositoryAdapter.saveAndFlush(entity);
        CredentialEntity loaded = credentialRepositoryAdapter.findById(saved.getId())
            .orElseThrow();
        return credentialPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<Credential> findById(Long id) {
        return credentialRepositoryAdapter.findById(id)
            .map(credentialPersistenceMapper::toDomain);
    }

    @Override
    public List<Credential> findByEmail(String email) {
        return credentialRepositoryAdapter.findByEmail(email)
            .stream()
            .map(credentialPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Credential> findByIsActive(Boolean isActive) {
        return credentialRepositoryAdapter.findByIsActive(isActive)
            .stream()
            .map(credentialPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<Credential> findAll() {
        return credentialRepositoryAdapter.findAll()
            .stream()
            .map(credentialPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public void deactivate(Long id) {
        credentialRepositoryAdapter.deactivate(id);
    }
}
