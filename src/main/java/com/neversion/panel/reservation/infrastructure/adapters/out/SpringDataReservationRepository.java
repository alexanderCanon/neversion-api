package com.neversion.panel.reservation.infrastructure.adapters.out;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataReservationRepository extends JpaRepository<ReservationEntity, UUID> {

    boolean existsByProofUrl(String proofUrl);
}
