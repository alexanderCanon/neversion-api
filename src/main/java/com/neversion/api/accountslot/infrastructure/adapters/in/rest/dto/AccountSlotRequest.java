package com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.Size;

public record AccountSlotRequest(
        @Size(max = 100) String profileName,
        @Size(max = 20) String pin) {
}
