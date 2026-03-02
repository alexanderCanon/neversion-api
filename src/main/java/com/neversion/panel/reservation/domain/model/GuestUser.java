package com.neversion.panel.reservation.domain.model;

import java.util.UUID;

public record GuestUser(
        UUID id,
        String name,
        String email,
        String phone) {
}
