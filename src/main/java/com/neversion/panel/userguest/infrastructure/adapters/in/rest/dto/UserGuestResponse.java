package com.neversion.panel.userguest.infrastructure.adapters.in.rest.dto;

import java.util.UUID;

public record UserGuestResponse(
    UUID id,
    String name,
    String email,
    String phone,
    Boolean isActive
) {

}
