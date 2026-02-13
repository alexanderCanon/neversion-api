package com.neversion.panel.credential.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.credential.application.port.in.GetCredentialUseCase;
import com.neversion.panel.credential.domain.model.Credential;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.dto.CredentialResponse;
import com.neversion.panel.credential.infrastructure.adapters.in.rest.mapper.CredentialMapper;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialGetController {

    private final GetCredentialUseCase getCredentialUseCase;
    private final CredentialMapper credentialMapper;

    public CredentialGetController(GetCredentialUseCase getCredentialUseCase, CredentialMapper credentialMapper) {
        this.getCredentialUseCase = getCredentialUseCase;
        this.credentialMapper = credentialMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CredentialResponse> getCredentialById(@PathVariable Long id) {
        Credential credential = getCredentialUseCase.getById(id);
        CredentialResponse response = credentialMapper.toResponse(credential);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getCredentials(
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Boolean isActive) {

        if (email != null && !email.isBlank()) {
            List<CredentialResponse> response = getCredentialUseCase.getByEmail(email).stream()
                .map(credentialMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (isActive != null) {
            List<CredentialResponse> response = getCredentialUseCase.getByIsActive(isActive).stream()
                .map(credentialMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        List<CredentialResponse> response = getCredentialUseCase.getAll().stream()
            .map(credentialMapper::toResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
}
