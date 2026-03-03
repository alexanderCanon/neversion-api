package com.neversion.panel.reservation.infrastructure.adapters.in.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.reservation.application.port.in.CreateReservationUseCase;
import com.neversion.panel.reservation.application.port.in.ReservationItemCommand;
import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationRequest;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationResponse;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.mapper.ReservationRestMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/reservations")
@Tag(name = "Reservations", description = "Reservation management for guest purchases")
public class ReservationController {

    private final CreateReservationUseCase createReservationUseCase;
    private final ReservationRestMapper reservationRestMapper;

    public ReservationController(
            CreateReservationUseCase createReservationUseCase,
            ReservationRestMapper reservationRestMapper) {
        this.createReservationUseCase = createReservationUseCase;
        this.reservationRestMapper = reservationRestMapper;
    }

    @PostMapping
    @Operation(summary = "Create a reservation", description = "Create a new reservation with guest info, items, and optional payment proof")
    @ApiResponse(responseCode = "201", description = "Reservation created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request, duplicate proof URL, or insufficient stock")
    public ResponseEntity<ReservationResponse> createReservation(
            @Valid @RequestBody ReservationRequest request) {

        GuestUser guest = reservationRestMapper.toGuestDomain(request);
        List<ReservationItemCommand> items = reservationRestMapper.toItemCommands(request.items());

        Reservation reservation = createReservationUseCase.create(guest, items, request.proofUrl());
        ReservationResponse response = reservationRestMapper.toResponse(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
