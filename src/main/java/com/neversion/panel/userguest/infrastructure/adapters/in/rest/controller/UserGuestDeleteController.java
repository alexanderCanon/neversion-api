package com.neversion.panel.userguest.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.userguest.application.port.in.DeactivateUserGuestUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/user-guests")
@Tag(name = "Guest Users")
public class UserGuestDeleteController {

    private final DeactivateUserGuestUseCase deactivateUserGuestUseCase;

    public UserGuestDeleteController(DeactivateUserGuestUseCase deactivateUserGuestUseCase) {
        this.deactivateUserGuestUseCase = deactivateUserGuestUseCase;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a guest user", description = "Soft-delete a guest user by its UUID")
    @ApiResponse(responseCode = "204", description = "Guest user deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Guest user not found")
    public ResponseEntity<Void> deactivateUserGuest(@PathVariable UUID id) {
        deactivateUserGuestUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
