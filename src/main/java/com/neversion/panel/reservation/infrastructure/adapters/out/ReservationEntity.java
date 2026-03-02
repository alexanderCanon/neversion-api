package com.neversion.panel.reservation.infrastructure.adapters.out;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@Builder
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_guest_id", nullable = false)
    private UUID userGuestId;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "profile_id")
    private UUID profileId;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    @Column(name = "proof_url", unique = true)
    private String proofUrl;

    @Column(name = "expiration_date", nullable = false)
    private OffsetDateTime expirationDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    public ReservationEntity() {
    }

    public ReservationEntity(UUID id, UUID userGuestId, Long inventoryId, UUID profileId,
            BigDecimal discount, Integer qty, ReservationStatus status, String proofUrl,
            OffsetDateTime expirationDate, OffsetDateTime createdAt) {
        this.id = id;
        this.userGuestId = userGuestId;
        this.inventoryId = inventoryId;
        this.profileId = profileId;
        this.discount = discount;
        this.qty = qty;
        this.status = status;
        this.proofUrl = proofUrl;
        this.expirationDate = expirationDate;
        this.createdAt = createdAt;
    }
}
