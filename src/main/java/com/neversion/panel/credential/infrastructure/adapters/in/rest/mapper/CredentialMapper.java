package com.neversion.panel.credential.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.dto.CredentialRequest;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.dto.CredentialResponse;

@Component
public class CredentialMapper {

    public Credential toDomain(CredentialRequest request) {
        return new Credential(
            null,
            request.getEmail(),
            request.getPass(),
            true,
            request.getServiceDetailsId(),
            null,
            null
        );
    }

    public CredentialResponse toResponse(Credential credential) {
        return new CredentialResponse(
            credential.id(),
            credential.email(),
            credential.pass(),
            credential.isActive(),
            credential.serviceDetailsId(),
            credential.serviceName(),
            credential.categoryName()
        );
    }
}
