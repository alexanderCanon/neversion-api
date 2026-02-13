package com.neversion.panel.credential.infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CredentialRequest {
    @NotBlank(message = "Email is required")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    String email;

    @NotBlank(message = "Pass is required")
    @Size(max = 255, message = "Pass must not exceed 255 characters")
    String pass;

    @NotNull(message = "Service details id is required")
    Long serviceDetailsId;
}
