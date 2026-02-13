package com.neversion.panel.credential.domain.model;

public record Credential(
    Long id,
    String email,
    String pass,
    Boolean isActive,
    Long serviceDetailsId
) {

}
