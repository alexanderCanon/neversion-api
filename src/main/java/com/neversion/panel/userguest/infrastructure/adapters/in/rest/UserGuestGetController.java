package com.neversion.panel.userguest.infrastructure.adapters.in.rest;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.userguest.application.port.in.GetUserGuestUseCase;
import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.mapper.UserGuestMapper;

@RestController
@RequestMapping("/api/v1/user-guests")
public class UserGuestGetController {

    private final GetUserGuestUseCase getUserGuestUseCase;
    private final UserGuestMapper userGuestMapper;

    public UserGuestGetController(GetUserGuestUseCase getUserGuestUseCase, UserGuestMapper userGuestMapper) {
        this.getUserGuestUseCase = getUserGuestUseCase;
        this.userGuestMapper = userGuestMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGuestResponse> getUserGuestById(@PathVariable UUID id) {
        UserGuest userGuest = getUserGuestUseCase.getById(id);
        UserGuestResponse response = userGuestMapper.toResponse(userGuest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getUserGuests(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) String phone) {

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
