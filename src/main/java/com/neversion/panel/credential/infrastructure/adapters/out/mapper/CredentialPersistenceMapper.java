package com.neversion.panel.credential.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.infrastructure.adapters.out.CredentialEntity;

@Component
public class CredentialPersistenceMapper {

    public Credential toDomain(CredentialEntity entity) {
        return new Credential(
            entity.getId(),
            entity.getEmail(),
            entity.getPass(),
            entity.getIsActive(),
            entity.getServiceDetailsId(),
            entity.getServiceDetail().getService().getName(),
            entity.getServiceDetail().getCategory().getName()
        );
    }

    public CredentialEntity toEntity(Credential credential) {
        return new CredentialEntity(
            credential.id(),
            credential.email(),
            credential.pass(),
            credential.isActive(),
            credential.serviceDetailsId()
        );
    }
}
