package com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AccountSlotResponse(
        UUID id,
        UUID accountId,
        String profileName,
        String pin,
        String status) {
}
