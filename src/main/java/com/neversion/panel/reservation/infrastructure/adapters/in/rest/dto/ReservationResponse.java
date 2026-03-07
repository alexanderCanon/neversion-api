package com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;

public record ReservationResponse(
                UUID id,
                UUID userGuestId,
                ReservationStatus status,
                BigDecimal discount,
                BigDecimal total,
                String receiptUrl,
                Instant expirationDate,
                Instant createdAt,
                List<ReservationDetailResponse> details) {
}
