package com.neversion.panel.reservation.application.port.in;

import java.util.UUID;

import com.neversion.panel.reservation.domain.model.Reservation;

/**
 * UC2: Report Payment — customer uploads receipt URL.
 */
public interface UploadReceiptUseCase {

    Reservation uploadReceipt(UUID reservationId, String receiptUrl);
}
