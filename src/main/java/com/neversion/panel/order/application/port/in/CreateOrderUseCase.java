package com.neversion.panel.order.application.port.in;

import java.util.UUID;

import com.neversion.panel.order.domain.model.Order;

/**
 * Creates an Order from a validated reservation.
 */
public interface CreateOrderUseCase {

    Order createFromReservation(UUID reservationId, String notes);
}
