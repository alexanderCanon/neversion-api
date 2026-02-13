package com.neversion.panel.credential.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.credential.application.port.in.CreateCredentialUseCase;
import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.dto.CredentialRequest;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.dto.CredentialResponse;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.mapper.CredentialMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialPostController {
    private final CreateCredentialUseCase createCredentialUseCase;
    private final CredentialMapper credentialMapper;

    public CredentialPostController(CreateCredentialUseCase createCredentialUseCase,
        CredentialMapper credentialMapper) {
        this.createCredentialUseCase = createCredentialUseCase;
        this.credentialMapper = credentialMapper;
    }

    @PostMapping
    public ResponseEntity<CredentialResponse> createCredential(@Valid @RequestBody CredentialRequest request) {
        Credential credential = credentialMapper.toDomain(request);
        Credential created = createCredentialUseCase.create(credential);
        CredentialResponse response = credentialMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
