package com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;

public record ReservationResponse(
        UUID id,
        ReservationStatus status,
        String proofUrl,
        OffsetDateTime expirationDate,
        OffsetDateTime createdAt,
        List<ReservationDetailResponse> details) {
}
