package com.neversion.panel.reservation.application.port.in;

import java.util.UUID;

import com.neversion.panel.reservation.domain.model.Reservation;

/**
 * UC3: Validate and Formalize Sale — admin validates payment.
 */
public interface ValidateReservationUseCase {

    Reservation validate(UUID reservationId, String notes);
}
