package com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record ProfileResponse(
        UUID id,
        Long accountId,
        String name,
        String pin,
        Boolean isOwner,
        LocalDateTime createdAt) {
}
