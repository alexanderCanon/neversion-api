package com.neversion.panel.userguest.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestRequest;
import com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto.UserGuestResponse;

@Component
public class UserGuestMapper {

    public UserGuest toDomain(UserGuestRequest request) {
        return request != null ? UserGuest.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build() : null;
    }

    public UserGuestResponse toResponse(UserGuest userGuest) {
        return userGuest != null ? UserGuestResponse.builder()
                .id(userGuest.getId())
                .name(userGuest.getName())
                .email(userGuest.getEmail())
                .phone(userGuest.getPhone())
                .build() : null;
    }
}
