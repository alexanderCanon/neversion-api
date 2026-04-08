package com.neversion.api.users.infrastructure.adapters.in.rest.dto;

public record UserResponse(
    String name,
    String lastname,
    String email,
    String phone,
    Boolean isActive
) {

}
