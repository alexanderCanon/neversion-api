package com.neversion.panel.booking.domain.model;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.booking.domain.model.enums.BookingState;

public record Booking(
    Long id,
    Long servicesDetails, // Clave foránea a services_details
    UUID userGuestId,
    Integer quantity,
    BookingState bookingState,
    LocalDate expirationDate,
    Instant createdAt
) {

}
