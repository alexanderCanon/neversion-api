package com.neversion.panel.subscription.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.subscription.domain.model.enums.SubStatus;

public record Subscription(
        Long id,
        UUID profilesId,
        UUID userGuestId,
        Long accountId,
        String profileName,
        String userGuestName,
        LocalDate purchaseDate,
        LocalDate renewalDate,
        String profile,
        String pin,
        Boolean isActive,
        SubStatus sStatus) {

}
