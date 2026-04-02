package com.neversion.api.userguest.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.userguest.application.port.in.UpdateUserGuestUseCase;
import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.infrastructure.adapters.in.rest.dto.UserGuestRequest;
import com.neversion.api.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;
import com.neversion.api.userguest.infrastructure.adapters.in.rest.mapper.UserGuestMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user-guests")
@Tag(name = "Guest Users")
public class UserGuestPutController {
    private final UpdateUserGuestUseCase updateUserGuestUseCase;
    private final UserGuestMapper userGuestMapper;

    public UserGuestPutController(UpdateUserGuestUseCase updateUserGuestUseCase, UserGuestMapper userGuestMapper) {
        this.updateUserGuestUseCase = updateUserGuestUseCase;
        this.userGuestMapper = userGuestMapper;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a guest user", description = "Update the details of an existing guest user")
    @ApiResponse(responseCode = "200", description = "Guest user updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "404", description = "Guest user not found")
    public ResponseEntity<UserGuestResponse> updateUserGuest(
            @PathVariable UUID id,
            @Valid @RequestBody UserGuestRequest request) {
        UserGuest userGuest = userGuestMapper.toDomain(request);
        userGuest.setId(id);
        UserGuest updated = updateUserGuestUseCase.update(userGuest);
        UserGuestResponse response = userGuestMapper.toResponse(updated);
        return ResponseEntity.ok(response);
    }
}
