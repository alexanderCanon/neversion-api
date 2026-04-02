package com.neversion.api.dashboard.application.result;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Subscription data nested inside a slot result.
 */
public record SlotSubscriptionResult(
        UUID subscriptionId,
        LocalDate startDate,
        LocalDate endDate,
        String status,
        SlotCustomerResult customer) {
}
