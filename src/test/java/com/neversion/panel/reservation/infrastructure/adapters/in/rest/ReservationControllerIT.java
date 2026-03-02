package com.neversion.panel.reservation.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neversion.panel.config.SecurityConfig;
import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.reservation.application.port.in.CreateReservationUseCase;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.mapper.ReservationRestMapper;

@WebMvcTest(ReservationController.class)
@Import(SecurityConfig.class)
@DisplayName("ReservationController Integration Tests")
class ReservationControllerIT {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CreateReservationUseCase createReservationUseCase;

        @MockitoBean
        private ReservationRestMapper reservationRestMapper;

        private static final String VALID_REQUEST = """
                        {
                            "guestName": "John Doe",
                            "guestEmail": "john@example.com",
                            "guestPhone": "555-0100",
                            "items": [
                                { "inventoryId": 1, "qty": 2 }
                            ],
                            "proofUrl": "https://bank.com/receipt/abc123"
                        }
                        """;

        // -- Happy path --

        @Test
        @DisplayName("POST /api/v1/reservations → 201 CREATED when request is valid")
        void createReservation_shouldReturn201_whenRequestIsValid() throws Exception {
                UUID reservationId = UUID.randomUUID();

                Reservation domain = Reservation.builder()
                                .id(reservationId)
                                .status(ReservationStatus.PENDING)
                                .proofUrl("https://bank.com/receipt/abc123")
                                .expirationDate(OffsetDateTime.now().plusMinutes(60))
                                .createdAt(OffsetDateTime.now())
                                .details(List.of(
                                                new ReservationDetail(UUID.randomUUID(), reservationId, 1L, 2,
                                                                new BigDecimal("9.99"))))
                                .build();

                when(reservationRestMapper.toGuestDomain(any())).thenCallRealMethod();
                when(reservationRestMapper.toItemCommands(any())).thenCallRealMethod();
                when(createReservationUseCase.create(any(), any(), anyString())).thenReturn(domain);
                when(reservationRestMapper.toResponse(any())).thenCallRealMethod();

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(VALID_REQUEST))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(reservationId.toString()))
                                .andExpect(jsonPath("$.status").value("PENDING"));
        }

        // -- Validation tests --

        @Test
        @DisplayName("POST /api/v1/reservations → 400 when guestEmail is blank")
        void createReservation_shouldReturn400_whenGuestEmailIsBlank() throws Exception {
                String body = """
                                {
                                    "guestName": "John Doe",
                                    "guestEmail": "",
                                    "guestPhone": "555-0100",
                                    "items": [{ "inventoryId": 1, "qty": 1 }],
                                    "proofUrl": "https://bank.com/receipt/abc"
                                }
                                """;

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("POST /api/v1/reservations → 400 when guestEmail is invalid")
        void createReservation_shouldReturn400_whenGuestEmailIsInvalid() throws Exception {
                String body = """
                                {
                                    "guestName": "John",
                                    "guestEmail": "not-an-email",
                                    "guestPhone": "555-0100",
                                    "items": [{ "inventoryId": 1, "qty": 1 }],
                                    "proofUrl": "https://bank.com/receipt/abc"
                                }
                                """;

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("POST /api/v1/reservations → 400 when items list is null")
        void createReservation_shouldReturn400_whenItemsIsNull() throws Exception {
                String body = """
                                {
                                    "guestName": "John",
                                    "guestEmail": "john@example.com",
                                    "guestPhone": "555-0100",
                                    "proofUrl": "https://bank.com/receipt/abc"
                                }
                                """;

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("POST /api/v1/reservations → 201 when proofUrl is omitted (optional)")
        void createReservation_shouldReturn201_whenProofUrlIsOmitted() throws Exception {
                UUID reservationId = UUID.randomUUID();
                String body = """
                                {
                                    "guestName": "John",
                                    "guestEmail": "john@example.com",
                                    "guestPhone": "555-0100",
                                    "items": [{ "inventoryId": 1, "qty": 1 }]
                                }
                                """;

                Reservation domain = Reservation.builder()
                                .id(reservationId)
                                .status(ReservationStatus.PENDING)
                                .expirationDate(OffsetDateTime.now().plusMinutes(60))
                                .createdAt(OffsetDateTime.now())
                                .details(List.of(
                                                new ReservationDetail(UUID.randomUUID(), reservationId, 1L, 1,
                                                                new BigDecimal("9.99"))))
                                .build();

                when(reservationRestMapper.toGuestDomain(any())).thenCallRealMethod();
                when(reservationRestMapper.toItemCommands(any())).thenCallRealMethod();
                when(createReservationUseCase.create(any(), any(), isNull())).thenReturn(domain);
                when(reservationRestMapper.toResponse(any())).thenCallRealMethod();

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isCreated());
        }

    @Test
    @DisplayName("POST /api/v1/reservations → 400 when proofUrl already used (fraud)")
    void createReservation_shouldReturn400_whenProofUrlIsDuplicate() throws Exception {
        when(reservationRestMapper.toGuestDomain(any())).thenCallRealMethod();
        when(reservationRestMapper.toItemCommands(any())).thenCallRealMethod();
        when(createReservationUseCase.create(any(), any(), anyString()))
                .thenThrow(new BusinessRuleException(
                        "The proof_url provided has already been used. Please upload a different payment receipt."));

        mockMvc.perform(post("/api/v1/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_REQUEST))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("proof_url")));
    }
}
