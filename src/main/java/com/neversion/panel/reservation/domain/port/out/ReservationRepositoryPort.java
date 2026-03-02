package com.neversion.panel.reservation.domain.port.out;

import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;

public interface ReservationRepositoryPort {

    boolean existsByProofUrl(String proofUrl);

    GuestUser findOrCreateGuest(GuestUser guest);

    Reservation save(Reservation reservation);

    ReservationDetail saveDetail(ReservationDetail detail);
}
