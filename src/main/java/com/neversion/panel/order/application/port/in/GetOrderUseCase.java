package com.neversion.panel.order.application.port.in;

import java.util.Optional;
import java.util.UUID;

import com.neversion.panel.order.domain.model.Order;

/**
 * Retrieves orders by ID or by linked reservation.
 */
public interface GetOrderUseCase {

    Optional<Order> getById(UUID id);

    Optional<Order> getByReservationId(UUID reservationId);
}
