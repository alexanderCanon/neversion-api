package com.neversion.api.subscription.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

/**
 * Request to create a Subscription (CU-A05):
 * Admin assigns a Client to a Profile of an Account.
 */
public record CreateSubscriptionRequest(
        @NotNull(message = "Profile ID is required") UUID profileId,

        @NotNull(message = "Client ID is required") UUID clientId,

        @NotNull(message = "Account ID is required") UUID accountId,

        @NotNull(message = "Purchase date is required") LocalDate purchaseDate,

        @NotNull(message = "Payment due date is required") LocalDate paymentDueDate,

        BigDecimal price,

        String notes) {
}
