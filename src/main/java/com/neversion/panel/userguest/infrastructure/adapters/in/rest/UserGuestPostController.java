package com.neversion.panel.userguest.infrastructure.adapters.in.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.userguest.application.port.in.CreateUserGuestUseCase;
import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestRequest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.mapper.UserGuestMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user-guests")
public class UserGuestPostController {
    private final CreateUserGuestUseCase createUserGuestUseCase;
    private final UserGuestMapper userGuestMapper;

    public UserGuestPostController(CreateUserGuestUseCase createUserGuestUseCase, UserGuestMapper userGuestMapper) {
        this.createUserGuestUseCase = createUserGuestUseCase;
        this.userGuestMapper = userGuestMapper;
    }

    @PostMapping
    public ResponseEntity<UserGuestResponse> createUserGuest(@Valid @RequestBody UserGuestRequest request) {
        UserGuest userGuest = userGuestMapper.toDomain(request);
        UserGuest created = createUserGuestUseCase.create(userGuest);
        UserGuestResponse response = userGuestMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
