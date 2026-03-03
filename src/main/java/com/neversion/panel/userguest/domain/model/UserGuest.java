package com.neversion.panel.userguest.domain.model;

import java.time.Instant;
import java.util.UUID;

import lombok.Builder;

@Builder
public record UserGuest(
        UUID id,
        String name,
        String email,
        String phone,
        Boolean isActive,
        Instant createdAt) {

}
