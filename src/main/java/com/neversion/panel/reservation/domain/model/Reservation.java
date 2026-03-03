package com.neversion.panel.reservation.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Reservation {

    private UUID id;
    private UUID userGuestId;
    private BigDecimal discount;
    private String proofUrl;
    private ReservationStatus status;
    private Instant expirationDate;
    private Instant createdAt;
    private List<ReservationDetail> details;

    public Reservation() {
    }

    public Reservation(UUID id, UUID userGuestId, BigDecimal discount,
            String proofUrl, ReservationStatus status, Instant expirationDate,
            Instant createdAt, List<ReservationDetail> details) {
        this.id = id;
        this.userGuestId = userGuestId;
        this.discount = discount;
        this.proofUrl = proofUrl;
        this.status = status;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
        this.details = details;
    }
}
