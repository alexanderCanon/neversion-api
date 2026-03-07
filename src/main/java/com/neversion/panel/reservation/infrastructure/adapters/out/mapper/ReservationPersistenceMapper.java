package com.neversion.panel.reservation.infrastructure.adapters.out.mapper;

import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.infrastructure.adapters.out.ReservationDetailEntity;
import com.neversion.panel.reservation.infrastructure.adapters.out.ReservationEntity;

@Component
public class ReservationPersistenceMapper {

    public Reservation toDomain(ReservationEntity entity) {
        return Reservation.builder()
                .id(entity.getId())
                .userGuestId(entity.getUserGuestId())
                .discount(entity.getDiscount())
                .total(entity.getTotal())
                .receiptUrl(entity.getReceiptUrl())
                .status(entity.getStatus())
                .expirationDate(entity.getExpirationDate().toInstant())
                .createdAt(entity.getCreatedAt().toInstant())
                .build();
    }

    public ReservationEntity toEntity(Reservation domain) {
        return ReservationEntity.builder()
                .id(domain.getId())
                .userGuestId(domain.getUserGuestId())
                .discount(domain.getDiscount())
                .total(domain.getTotal())
                .receiptUrl(domain.getReceiptUrl())
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
                entity.getUnitPrice(),
                entity.getSubtotal());
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
