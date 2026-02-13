package com.neversion.panel.profile.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.profile.application.port.in.DeactivateProfileUseCase;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileDeleteController {

    private final DeactivateProfileUseCase deactivateProfileUseCase;

    public ProfileDeleteController(DeactivateProfileUseCase deactivateProfileUseCase) {
        this.deactivateProfileUseCase = deactivateProfileUseCase;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateProfile(@PathVariable UUID id) {
        deactivateProfileUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
