package com.neversion.panel.reservation.infrastructure.adapters.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReservationDetailRepository extends JpaRepository<ReservationDetailEntity, UUID> {
}
