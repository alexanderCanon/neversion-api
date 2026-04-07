package com.neversion.api.reservation.application.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.reservation.application.port.in.CreateReservationUseCase;
import com.neversion.api.reservation.application.port.in.ReservationItemCommand;
import com.neversion.api.reservation.domain.model.Reservation;
import com.neversion.api.reservation.domain.model.ReservationDetail;
import com.neversion.api.reservation.domain.model.enums.ReservationStatus;
import com.neversion.api.reservation.domain.port.out.ReservationRepositoryPort;
import com.neversion.api.reservation.domain.service.ReservationPricingService;

/**
 * UC1: Create Reservation (Checkout).
 * <p>
 * The guest user ID is optional at creation — can be attached later via
 * PUT /reservations/{id}/guest. Persists the reservation header with
 * status = PENDING, saves each item capturing the current price (BR-02),
 * and computes the total applying combo discount (BR-03).
 * </p>
 */
@Service
public class CreateReservationService implements CreateReservationUseCase {

    private static final int EXPIRATION_MINUTES = 60;

    private final ReservationRepositoryPort reservationRepositoryPort;
    private final GetInventoryUseCase getInventoryUseCase;
    private final ReservationPricingService reservationPricingService;

    public CreateReservationService(
            ReservationRepositoryPort reservationRepositoryPort,
            GetInventoryUseCase getInventoryUseCase,
            ReservationPricingService reservationPricingService) {
        this.reservationRepositoryPort = reservationRepositoryPort;
        this.getInventoryUseCase = getInventoryUseCase;
        this.reservationPricingService = reservationPricingService;
    }

    @Override
    @Transactional
    public Reservation create(UUID userGuestId, List<ReservationItemCommand> items) {

        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime expirationDate = now.plusMinutes(EXPIRATION_MINUTES);

        // Build reservation details with frozen prices (BR-02)
        List<ReservationDetail> detailsToSave = new ArrayList<>();
        for (ReservationItemCommand item : items) {
            Inventory inventory = getInventoryUseCase.getById(item.inventoryId());
            detailsToSave.add(new ReservationDetail(
                    null,
                    null, // reservationId set after save
                    item.inventoryId(),
                    item.qty(),
                    inventory.getPrice(),
                    null)); // subtotal is DB-computed
        }

        // Calculate pricing using domain service (BR-03)
        BigDecimal grossTotal = reservationPricingService.calculateGrossTotal(detailsToSave);
        BigDecimal discount = reservationPricingService.calculateComboDiscount(grossTotal, items.size());
        BigDecimal finalTotal = reservationPricingService.calculateFinalTotal(grossTotal, discount);

        Reservation reservation = Reservation.builder()
                .userGuestId(userGuestId) // nullable — can be attached later
                .status(ReservationStatus.PENDING)
                .discount(discount)
                .total(finalTotal)
                .expirationDate(expirationDate.toInstant())
                .build();

        Reservation savedReservation = reservationRepositoryPort.save(reservation);

        // Persist each detail linked to the saved reservation
        List<ReservationDetail> savedDetails = new ArrayList<>();
        for (ReservationDetail detail : detailsToSave) {
            ReservationDetail linked = new ReservationDetail(
                    null,
                    savedReservation.getId(),
                    detail.inventoryId(),
                    detail.qty(),
                    detail.unitPrice(),
                    null);
            savedDetails.add(reservationRepositoryPort.saveDetail(linked));
        }

        savedReservation.setDetails(savedDetails);
        return savedReservation;
    }
}
