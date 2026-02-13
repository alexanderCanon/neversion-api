package com.neversion.panel.userguest.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.userguest.application.port.in.DeactivateUserGuestUseCase;

@RestController
@RequestMapping("/api/v1/user-guests")
public class UserGuestDeleteController {

    private final DeactivateUserGuestUseCase deactivateUserGuestUseCase;

    public UserGuestDeleteController(DeactivateUserGuestUseCase deactivateUserGuestUseCase) {
        this.deactivateUserGuestUseCase = deactivateUserGuestUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUserGuest(@PathVariable UUID id) {
        deactivateUserGuestUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
