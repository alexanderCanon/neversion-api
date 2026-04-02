package com.neversion.api.dashboard.application.result;

import java.util.UUID;

/**
 * Read-only projection for endpoint 3: slot with optional subscription.
 */
public record AccountSlotResult(
        UUID slotId,
        String profileName,
        String pin,
        String slotStatus,
        SlotSubscriptionResult subscription) {
}
