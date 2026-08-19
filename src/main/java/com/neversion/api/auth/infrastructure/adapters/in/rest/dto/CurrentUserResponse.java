package com.neversion.api.auth.infrastructure.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record CurrentUserResponse(

        @Schema(description = "External Supabase subject linked to the platform user")
        String externalId,

        @Schema(description = "Platform role in lowercase")
        String role
) {
}
