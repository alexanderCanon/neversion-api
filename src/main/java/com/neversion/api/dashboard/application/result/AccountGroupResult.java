package com.neversion.api.dashboard.application.result;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Read-only projection for endpoint 2: account with slot availability.
 */
public record AccountGroupResult(
        UUID accountId,
        String email,
        String password,
        LocalDate cutOffDate,
        String accountType,
        String accountStatus,
        int maxSlots,
        int occupiedSlots,
        int availableSlots,
        String availability) {
}
