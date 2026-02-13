package com.neversion.panel.credential.infrastructure.adapters.in.rest.dto;

public record CredentialResponse(
    Long id,
    String email,
    String pass,
    Boolean isActive,
    Long serviceDetailsId,
    String serviceName,
    String categoryName
) {

}
