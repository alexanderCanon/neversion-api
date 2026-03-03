package com.neversion.panel.account.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.account.application.port.in.DeactivateAccountUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts")
public class AccountDeleteController {

    private final DeactivateAccountUseCase deactivateAccountUseCase;

    public AccountDeleteController(DeactivateAccountUseCase deactivateAccountUseCase) {
        this.deactivateAccountUseCase = deactivateAccountUseCase;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate an account", description = "Soft-delete an account by its UUID")
    @ApiResponse(responseCode = "204", description = "Account deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Account not found")
    public ResponseEntity<Void> deactivateAccount(@PathVariable UUID id) {
        deactivateAccountUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
