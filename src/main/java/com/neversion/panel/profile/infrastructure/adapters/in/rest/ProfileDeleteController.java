package com.neversion.panel.profile.infrastructure.adapters.in.rest;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.profile.application.port.in.DeactivateProfileUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/profiles")
@Tag(name = "Profiles")
public class ProfileDeleteController {

    private final DeactivateProfileUseCase deactivateProfileUseCase;

    public ProfileDeleteController(DeactivateProfileUseCase deactivateProfileUseCase) {
        this.deactivateProfileUseCase = deactivateProfileUseCase;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deactivate a profile", description = "Soft-delete a profile by its UUID")
    @ApiResponse(responseCode = "204", description = "Profile deactivated successfully")
    @ApiResponse(responseCode = "404", description = "Profile not found")
    public ResponseEntity<Void> deactivateProfile(@PathVariable UUID id) {
        deactivateProfileUseCase.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
