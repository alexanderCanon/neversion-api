// package com.neversion.api.profile.infrastructure.adapters.in.rest;

// import java.util.List;
// import java.util.UUID;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.neversion.api.profile.application.port.in.GetProfileUseCase;
// import com.neversion.api.profile.domain.model.Profile;
// import
// com.neversion.api.profile.infrastructure.adapters.in.rest.dto.ProfileResponse;
// import
// com.neversion.api.profile.infrastructure.adapters.in.rest.mapper.ProfileMapper;

// import io.swagger.v3.oas.annotations.Operation;
// import io.swagger.v3.oas.annotations.Parameter;
// import io.swagger.v3.oas.annotations.responses.ApiResponse;
// import io.swagger.v3.oas.annotations.tags.Tag;

// @RestController
// @RequestMapping("/api/v1/profiles")
// @Tag(name = "Profiles", description = "User profile management")
// public class ProfileGetController {

// private final GetProfileUseCase getProfileUseCase;
// private final ProfileMapper profileMapper;

// public ProfileGetController(GetProfileUseCase getProfileUseCase,
// ProfileMapper profileMapper) {
// this.getProfileUseCase = getProfileUseCase;
// this.profileMapper = profileMapper;
// }

// @GetMapping("/{id}")
// @Operation(summary = "Get profile by ID", description = "Retrieve a single
// profile by its UUID")
// @ApiResponse(responseCode = "200", description = "Profile found")
// @ApiResponse(responseCode = "404", description = "Profile not found")
// public ResponseEntity<ProfileResponse> getProfileById(@PathVariable UUID id)
// {
// Profile profile = getProfileUseCase.getById(id);
// ProfileResponse response = profileMapper.toResponse(profile);
// return ResponseEntity.ok(response);
// }

// @GetMapping
// @Operation(summary = "Get profiles", description = "Retrieve profiles
// filtered by name, email, or active status")
// @ApiResponse(responseCode = "200", description = "Profiles retrieved
// successfully")
// public ResponseEntity<?> getProfiles(
// @Parameter(description = "Filter by profile name") @RequestParam(required =
// false) String name,
// @Parameter(description = "Filter by exact email address")
// @RequestParam(required = false) String email,
// @Parameter(description = "Filter by active status") @RequestParam(required =
// false) Boolean isActive) {

// if (email != null && !email.isBlank()) {
// Profile profile = getProfileUseCase.getByEmail(email);
// ProfileResponse response = profileMapper.toResponse(profile);
// return ResponseEntity.ok(response);
// }

// if (name != null && !name.isBlank()) {
// List<ProfileResponse> response = getProfileUseCase.getByName(name).stream()
// .map(profileMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }

// if (isActive != null) {
// List<ProfileResponse> response =
// getProfileUseCase.getByIsActive(isActive).stream()
// .map(profileMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }

// List<ProfileResponse> response = getProfileUseCase.getAll().stream()
// .map(profileMapper::toResponse)
// .toList();
// return ResponseEntity.ok(response);
// }
// }
