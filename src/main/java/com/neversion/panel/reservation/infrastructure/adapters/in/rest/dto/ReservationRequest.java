package com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ReservationRequest(

                @NotBlank String guestName,
                @NotBlank @Email String guestEmail,
                @NotBlank String guestPhone,
                @NotNull @NotEmpty List<@Valid ReservationItemRequest> items,
                String proofUrl) {
}
