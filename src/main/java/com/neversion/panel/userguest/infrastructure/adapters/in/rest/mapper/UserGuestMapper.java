package com.neversion.panel.userguest.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestRequest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;

@Component
public class UserGuestMapper {

    public UserGuest toDomain(UserGuestRequest request) {
        return new UserGuest(
            null,
            request.getName(),
            request.getEmail(),
            request.getPhone(),
            true,
            null
        );
    }

    public UserGuestResponse toResponse(UserGuest userGuest) {
        return new UserGuestResponse(
            userGuest.id(),
            userGuest.name(),
            userGuest.email(),
            userGuest.phone(),
            userGuest.isActive()
        );
    }
}
