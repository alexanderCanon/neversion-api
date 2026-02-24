package com.neversion.panel.account.infrastructure.adapters.in.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.account.application.port.in.DeactivateAccountUseCase;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountDeleteController {

    private final DeactivateAccountUseCase deactivateAccountUseCase;

    public AccountDeleteController(DeactivateAccountUseCase deactivateAccountUseCase) {
        this.deactivateAccountUseCase = deactivateAccountUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateAccount(@PathVariable Long id) {
        deactivateAccountUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
