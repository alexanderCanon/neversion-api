package com.neversion.panel.reservation.infrastructure.adapters.out.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.infrastructure.adapters.out.GuestUserEntity;
import com.neversion.panel.reservation.infrastructure.adapters.out.ReservationDetailEntity;
import com.neversion.panel.reservation.infrastructure.adapters.out.ReservationEntity;

@Component
public class ReservationPersistenceMapper {

    public GuestUser toDomain(GuestUserEntity entity) {
        return new GuestUser(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone());
    }

    public GuestUserEntity toEntity(GuestUser domain) {
        return GuestUserEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .email(domain.email())
                .phone(domain.phone())
                .build();
    }

    public Reservation toDomain(ReservationEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .userGuestId(entity.getUserGuestId())
                .discount(entity.getDiscount())
                .proofUrl(entity.getProofUrl())
                .status(entity.getStatus())
                .expirationDate(entity.getExpirationDate().toInstant())
                .createdAt(entity.getCreatedAt().toInstant())
                .build();
    }

    public ReservationEntity toEntity(Reservation domain) {
        // Use the first detail's inventoryId and qty for the parent row (schema
        // constraint)
        Long inventoryId = null;
        Integer qty = 1;
        if (domain.getDetails() != null && !domain.getDetails().isEmpty()) {
            inventoryId = domain.getDetails().get(0).inventoryId();
            qty = domain.getDetails().get(0).qty();
        }

        return ReservationEntity.builder()
                .id(domain.getId())
                .userGuestId(domain.getUserGuestId())
                .discount(domain.getDiscount())
                .inventoryId(inventoryId)
                .qty(qty)
                .proofUrl(domain.getProofUrl())
                .status(domain.getStatus())
                .expirationDate(domain.getExpirationDate().atOffset(ZoneOffset.UTC))
                .build();
    }

    public ReservationDetail toDomain(ReservationDetailEntity entity) {
        return new ReservationDetail(
                entity.getId(),
                entity.getReservationId(),
                entity.getInventoryId(),
                entity.getQty(),
                entity.getUnitPrice());
    }

    public ReservationDetailEntity toEntity(ReservationDetail domain) {
        return ReservationDetailEntity.builder()
                .id(domain.id())
                .reservationId(domain.reservationId())
                .inventoryId(domain.inventoryId())
                .qty(domain.qty())
                .unitPrice(domain.unitPrice())
                .build();
    }
}
