package com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateSubscriptionRequest(
                @NotNull(message = "Account ID is required") UUID accountId,

                @NotNull(message = "User guest ID is required") UUID userGuestId,

                @NotNull(message = "Purchase date is required") LocalDate purchaseDate,

                @NotNull(message = "Renewal date is required") LocalDate renewalDate,

                UUID accountSlotId,

                UUID orderId) {
}
