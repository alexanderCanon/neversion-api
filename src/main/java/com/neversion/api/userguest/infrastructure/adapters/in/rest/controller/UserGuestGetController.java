package com.neversion.api.userguest.infrastructure.adapters.in.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.userguest.application.port.in.GetUserGuestUseCase;
import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;
import com.neversion.api.userguest.infrastructure.adapters.in.rest.mapper.UserGuestMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/user-guests")
@Tag(name = "Guest Users", description = "Guest user management for reservations")
public class UserGuestGetController {

    private final GetUserGuestUseCase getUserGuestUseCase;
    private final UserGuestMapper userGuestMapper;

    public UserGuestGetController(GetUserGuestUseCase getUserGuestUseCase, UserGuestMapper userGuestMapper) {
        this.getUserGuestUseCase = getUserGuestUseCase;
        this.userGuestMapper = userGuestMapper;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get guest user by ID", description = "Retrieve a single guest user by its UUID")
    @ApiResponse(responseCode = "200", description = "Guest user found")
    @ApiResponse(responseCode = "404", description = "Guest user not found")
    public ResponseEntity<UserGuestResponse> getUserGuestById(@PathVariable UUID id) {
        UserGuest userGuest = getUserGuestUseCase.getById(id);
        UserGuestResponse response = userGuestMapper.toResponse(userGuest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get guest users", description = "Retrieve guest users filtered by name or phone")
    @ApiResponse(responseCode = "200", description = "Guest users retrieved successfully")
    public ResponseEntity<?> getUserGuests(
            @Parameter(description = "Filter by guest name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by phone number") @RequestParam(required = false) String phone) {

        if (name != null && !name.isBlank()) {
            List<UserGuestResponse> response = getUserGuestUseCase.getByName(name).stream()
                    .map(userGuestMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        if (phone != null && !phone.isBlank()) {
            List<UserGuestResponse> response = getUserGuestUseCase.getByPhone(phone).stream()
                    .map(userGuestMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }

        List<UserGuestResponse> response = getUserGuestUseCase.getAll().stream()
                .map(userGuestMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
