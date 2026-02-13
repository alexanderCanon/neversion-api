package com.neversion.panel.subscription.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.subscription.domain.model.enums.SubStatus;

public record Subscription(
    Long id,
    UUID profilesId,
    Long credentialsId,
    LocalDate purchaseDate,
    LocalDate renewalDate,
    String profile,
    String pin,
    Boolean isActive,
    SubStatus sStatus,
    UUID userGuestId
) {

}
