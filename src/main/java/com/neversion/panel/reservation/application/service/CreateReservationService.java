package com.neversion.panel.reservation.application.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.reservation.application.port.in.CreateReservationUseCase;
import com.neversion.panel.reservation.application.port.in.ReservationItemCommand;
import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;

@Service
public class CreateReservationService implements CreateReservationUseCase {

    private static final int EXPIRATION_MINUTES = 60;

    private final ReservationRepositoryPort reservationRepositoryPort;
    private final GetInventoryUseCase getInventoryUseCase;

    public CreateReservationService(
            ReservationRepositoryPort reservationRepositoryPort,
            GetInventoryUseCase getInventoryUseCase) {
        this.reservationRepositoryPort = reservationRepositoryPort;
        this.getInventoryUseCase = getInventoryUseCase;
    }

    @Override
    @Transactional
    public Reservation create(GuestUser guest, List<ReservationItemCommand> items, String proofUrl) {

        // Only check anti-fraud if proofUrl is provided
        if (proofUrl != null && !proofUrl.isBlank()) {
            if (reservationRepositoryPort.existsByProofUrl(proofUrl)) {
                throw new BusinessRuleException(
                        "The proof_url provided has already been used. Please upload a different payment receipt.");
            }
        }

        GuestUser savedGuest = reservationRepositoryPort.findOrCreateGuest(guest);

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expirationDate = now.plusMinutes(EXPIRATION_MINUTES);

        Reservation reservation = Reservation.builder()
                .userGuestId(savedGuest.id())
                .proofUrl(proofUrl)
                .status(ReservationStatus.PENDING)
                .expirationDate(expirationDate)
                .build();

        Reservation savedReservation = reservationRepositoryPort.save(reservation);

        List<ReservationDetail> details = new ArrayList<>();
        for (ReservationItemCommand item : items) {
            Inventory inventory = getInventoryUseCase.getById(item.inventoryId());

            ReservationDetail detail = new ReservationDetail(
                    null,
                    savedReservation.getId(),
                    item.inventoryId(),
                    item.qty(),
                    inventory.getPrice());

            ReservationDetail savedDetail = reservationRepositoryPort.saveDetail(detail);
            details.add(savedDetail);
        }

        savedReservation.setDetails(details);
        return savedReservation;
    }
}
