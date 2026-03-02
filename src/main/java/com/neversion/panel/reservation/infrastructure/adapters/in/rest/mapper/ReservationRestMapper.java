package com.neversion.panel.reservation.infrastructure.adapters.in.rest.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.neversion.panel.reservation.application.port.in.ReservationItemCommand;
import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationDetailResponse;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationItemRequest;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationRequest;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto.ReservationResponse;

@Component
public class ReservationRestMapper {

    public GuestUser toGuestDomain(ReservationRequest request) {
        return new GuestUser(null, request.guestName(), request.guestEmail(), request.guestPhone());
    }

    public List<ReservationItemCommand> toItemCommands(List<ReservationItemRequest> items) {
        if (items == null) {
            return Collections.emptyList();
        }
        return items.stream()
                .map(item -> new ReservationItemCommand(item.inventoryId(), item.qty()))
                .collect(Collectors.toList());
    }

    public ReservationResponse toResponse(Reservation reservation) {
        List<ReservationDetailResponse> detailResponses = reservation.getDetails() == null
                ? Collections.emptyList()
                : reservation.getDetails().stream()
                        .map(this::toDetailResponse)
                        .collect(Collectors.toList());

        return new ReservationResponse(
                reservation.getId(),
                reservation.getStatus(),
                reservation.getProofUrl(),
                reservation.getExpirationDate(),
                reservation.getCreatedAt(),
                detailResponses);
    }

    private ReservationDetailResponse toDetailResponse(ReservationDetail detail) {
        return new ReservationDetailResponse(
                detail.id(),
                detail.inventoryId(),
                detail.qty(),
                detail.unitPrice());
    }
}
