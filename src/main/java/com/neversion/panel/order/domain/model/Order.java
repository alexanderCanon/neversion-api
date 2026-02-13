package com.neversion.panel.order.domain.model;

import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;

public record Order(
    Long id,
    UUID profileId,
    UUID userGuestId,
    BigDecimal total,
    String proofUrl,
    String notes,
    Instant createdAt
) {

}
