package com.neversion.panel.reservation.application.port.in;

import java.util.List;

import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;

public interface CreateReservationUseCase {

    Reservation create(GuestUser guest, List<ReservationItemCommand> items, String proofUrl);
}
