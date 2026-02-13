package com.neversion.panel.credential.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.credential.application.port.in.DeactivateCredentialUseCase;

@RestController
@RequestMapping("/api/v1/credentials")
public class CredentialDeleteController {

    private final DeactivateCredentialUseCase deactivateCredentialUseCase;

    public CredentialDeleteController(DeactivateCredentialUseCase deactivateCredentialUseCase) {
        this.deactivateCredentialUseCase = deactivateCredentialUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateCredential(@PathVariable Long id) {
        deactivateCredentialUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
