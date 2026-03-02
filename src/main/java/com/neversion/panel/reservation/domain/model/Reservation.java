package com.neversion.panel.reservation.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    private UUID profileId;
    private BigDecimal discount;
    private String proofUrl;
    private ReservationStatus status;
    private OffsetDateTime expirationDate;
    private OffsetDateTime createdAt;
    private List<ReservationDetail> details;

    public Reservation() {
    }

    public Reservation(UUID id, UUID userGuestId, UUID profileId, BigDecimal discount,
            String proofUrl, ReservationStatus status, OffsetDateTime expirationDate,
            OffsetDateTime createdAt, List<ReservationDetail> details) {
        this.id = id;
        this.userGuestId = userGuestId;
        this.profileId = profileId;
        this.discount = discount;
        this.proofUrl = proofUrl;
        this.status = status;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
        this.details = details;
    }
}
