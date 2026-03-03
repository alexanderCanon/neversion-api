package com.neversion.panel.order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.neversion.panel.order.domain.model.enums.OrderStatus;

public record Order(
                UUID id,
                UUID reservationId,
                UUID userGuestId,
                BigDecimal discount,
                BigDecimal total,
                OrderStatus status,
                String proofUrl,
                String notes,
                Instant createdAt) {

}
