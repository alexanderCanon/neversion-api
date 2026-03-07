package com.neversion.panel.reservation.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.panel.reservation.domain.model.Reservation;

public interface CreateReservationUseCase {

    Reservation create(UUID userGuestId, List<ReservationItemCommand> items);
}
