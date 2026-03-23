package com.neversion.api.subscription.infrastructure.adapters.in.rest.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.subscription.domain.model.enums.SubStatus;

import lombok.Builder;

@Builder
public record SubscriptionResponse(
        UUID id,
        UUID accountId,
        UUID userGuestId,
        UUID accountSlotId,
        UUID orderId,
        LocalDate purchaseDate,
        LocalDate renewalDate,
        SubStatus status) {
}
