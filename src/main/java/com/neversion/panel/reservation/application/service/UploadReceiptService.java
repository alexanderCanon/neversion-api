package com.neversion.panel.reservation.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.reservation.application.port.in.UploadReceiptUseCase;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;

/**
 * UC2: Report Payment.
 * <p>
 * The customer uploads the receipt image URL. The system validates anti-fraud
 * (BR-05: unique receipt_url) and transitions the reservation from PENDING →
 * UPLOADED.
 * </p>
 */
@Service
public class UploadReceiptService implements UploadReceiptUseCase {

    private final ReservationRepositoryPort reservationRepositoryPort;

    public UploadReceiptService(ReservationRepositoryPort reservationRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
    }

    @Override
    @Transactional
    public Reservation uploadReceipt(UUID reservationId, String receiptUrl) {

        Reservation reservation = reservationRepositoryPort.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Reservation not found with id: " + reservationId));

        // Only PENDING reservations can receive a receipt
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new BusinessRuleException(
                    "Cannot upload receipt for a reservation with status: " + reservation.getStatus());
        }

        // BR-05: Anti-fraud — same receipt URL cannot be used twice
        if (reservationRepositoryPort.existsByReceiptUrl(receiptUrl)) {
            throw new BusinessRuleException(
                    "The receipt URL provided has already been used. Please upload a different payment receipt.");
        }

        reservation.setReceiptUrl(receiptUrl);
        reservation.setStatus(ReservationStatus.UPLOADED);

        return reservationRepositoryPort.update(reservation);
    }
}
