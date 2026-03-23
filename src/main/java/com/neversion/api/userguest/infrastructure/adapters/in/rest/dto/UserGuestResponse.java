package com.neversion.api.userguest.infrastructure.adapters.in.rest.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserGuestResponse(
                UUID id,
                String name,
                String email,
                String phone) {

}
