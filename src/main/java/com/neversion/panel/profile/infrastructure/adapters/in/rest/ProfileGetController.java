package com.neversion.panel.profile.infrastructure.adapters.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.profile.application.port.in.GetProfileUseCase;
import com.neversion.panel.profile.domain.model.Profile;
import com.neversion.panel.profile.infrastructure.adapters.in.rest.dto.ProfileResponse;
import com.neversion.panel.profile.infrastructure.adapters.in.rest.mapper.ProfileMapper;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileGetController {

    private final GetProfileUseCase getProfileUseCase;
    private final ProfileMapper profileMapper;

    public ProfileGetController(GetProfileUseCase getProfileUseCase, ProfileMapper profileMapper) {
        this.getProfileUseCase = getProfileUseCase;
        this.profileMapper = profileMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfileById(@PathVariable UUID id) {
        Profile profile = getProfileUseCase.getById(id);
        ProfileResponse response = profileMapper.toResponse(profile);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getProfiles(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Boolean isActive) {

        if (email != null && !email.isBlank()) {
            Profile profile = getProfileUseCase.getByEmail(email);
            ProfileResponse response = profileMapper.toResponse(profile);
            return ResponseEntity.ok(response);
        }

        if (name != null && !name.isBlank()) {
            List<ProfileResponse> response = getProfileUseCase.getByName(name).stream()
                .map(profileMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        if (isActive != null) {
            List<ProfileResponse> response = getProfileUseCase.getByIsActive(isActive).stream()
                .map(profileMapper::toResponse)
                .toList();
            return ResponseEntity.ok(response);
        }

        List<ProfileResponse> response = getProfileUseCase.getAll().stream()
            .map(profileMapper::toResponse)
            .toList();
        return ResponseEntity.ok(response);
    }
}
