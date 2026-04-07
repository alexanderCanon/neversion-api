package com.neversion.api.profile.infrastructure.adapters.in.rest.dto;

public record ProfileResponse(
    String name,
    String lastname,
    String email,
    String phone,
    Boolean isActive
) {

}
