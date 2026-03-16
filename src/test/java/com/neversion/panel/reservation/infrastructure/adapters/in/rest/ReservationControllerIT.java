package com.neversion.panel.reservation.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

// import com.fasterxml.jackson.databind.ObjectMapper;
import com.neversion.panel.BaseIntegrationTest;
import com.neversion.panel.config.SecurityConfig;
import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.reservation.application.port.in.CreateReservationUseCase;
import com.neversion.panel.reservation.application.port.in.UploadReceiptUseCase;
import com.neversion.panel.reservation.application.port.in.ValidateReservationUseCase;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;
import com.neversion.panel.reservation.infrastructure.adapters.in.rest.mapper.ReservationRestMapper;

@WebMvcTest(ReservationController.class)
@Import(SecurityConfig.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DisplayName("ReservationController Slicing Tests")
class ReservationControllerIT extends BaseIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        // @Autowired
        // private ObjectMapper objectMapper;

        @MockitoBean
        private CreateReservationUseCase createReservationUseCase;

        @MockitoBean
        private UploadReceiptUseCase uploadReceiptUseCase;

        @MockitoBean
        private ValidateReservationUseCase validateReservationUseCase;

        @MockitoBean
        private ReservationRepositoryPort reservationRepositoryPort;

        @MockitoBean
        private ReservationRestMapper reservationRestMapper;

        private static final UUID USER_GUEST_ID = UUID.randomUUID();

        private String validRequest() {
                return """
                                {
                                    "userGuestId": "%s",
                                    "items": [
                                        { "inventoryId": 1, "qty": 2 }
                                    ]
                                }
                                """.formatted(USER_GUEST_ID);
        }

        // ── UC1: Create Reservation ─────────────────────────────────────

        @Test
        @DisplayName("POST /api/v1/reservations → 201 CREATED when request is valid")
        void createReservation_shouldReturn201_whenRequestIsValid() throws Exception {
                UUID reservationId = UUID.randomUUID();

                Reservation domain = Reservation.builder()
                                .id(reservationId)
                                .userGuestId(USER_GUEST_ID)
                                .status(ReservationStatus.PENDING)
                                .total(new BigDecimal("19.98"))
                                .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                                .createdAt(Instant.now())
                                .details(List.of(
                                                new ReservationDetail(UUID.randomUUID(), reservationId, 1L, 2,
                                                                new BigDecimal("9.99"), new BigDecimal("19.98"))))
                                .build();

                when(reservationRestMapper.toItemCommands(any())).thenCallRealMethod();
                when(createReservationUseCase.create(eq(USER_GUEST_ID), any())).thenReturn(domain);
                when(reservationRestMapper.toResponse(any())).thenCallRealMethod();

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(validRequest()))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").value(reservationId.toString()))
                                .andExpect(jsonPath("$.status").value("PENDING"))
                                .andExpect(jsonPath("$.total").value(19.98));
        }

        // ── Validation tests ────────────────────────────────────────────


        @Test
        @DisplayName("POST /api/v1/reservations → 400 when items list is null")
        void createReservation_shouldReturn400_whenItemsIsNull() throws Exception {
                String body = """
                                {
                                    "userGuestId": "%s"
                                }
                                """.formatted(USER_GUEST_ID);

                mockMvc.perform(post("/api/v1/reservations")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                                .andExpect(status().isBadRequest());
        }

        // ── UC2: Upload Receipt ─────────────────────────────────────────

        @Test
        @DisplayName("PUT /api/v1/reservations/{id}/receipt → 200 when receipt uploaded")
        void uploadReceipt_shouldReturn200_whenValid() throws Exception {
                UUID reservationId = UUID.randomUUID();
                String receiptUrl = "https://bank.com/receipt/abc123";

                Reservation domain = Reservation.builder()
                                .id(reservationId)
                                .status(ReservationStatus.UPLOADED)
                                .receiptUrl(receiptUrl)
                                .total(new BigDecimal("19.98"))
                                .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                                .createdAt(Instant.now())
                                .build();

                when(uploadReceiptUseCase.uploadReceipt(reservationId, receiptUrl)).thenReturn(domain);
                when(reservationRestMapper.toResponse(any())).thenCallRealMethod();

                mockMvc.perform(put("/api/v1/reservations/{id}/receipt", reservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "receiptUrl": "https://bank.com/receipt/abc123" }
                                                """))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("UPLOADED"))
                                .andExpect(jsonPath("$.receiptUrl").value(receiptUrl));
        }

        @Test
        @DisplayName("PUT /api/v1/reservations/{id}/receipt → 400 when receiptUrl is blank")
        void uploadReceipt_shouldReturn400_whenReceiptUrlIsBlank() throws Exception {
                UUID reservationId = UUID.randomUUID();

                mockMvc.perform(put("/api/v1/reservations/{id}/receipt", reservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "receiptUrl": "" }
                                                """))
                                .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("PUT /api/v1/reservations/{id}/receipt → 400 when receipt URL is duplicate (BR-05)")
        void uploadReceipt_shouldReturn400_whenReceiptUrlIsDuplicate() throws Exception {
                UUID reservationId = UUID.randomUUID();

                when(uploadReceiptUseCase.uploadReceipt(eq(reservationId), anyString()))
                                .thenThrow(new BusinessRuleException(
                                                "The receipt URL provided has already been used. Please upload a different payment receipt."));

                mockMvc.perform(put("/api/v1/reservations/{id}/receipt", reservationId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                                { "receiptUrl": "https://bank.com/receipt/duplicate" }
                                                """))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$.message").value(
                                                org.hamcrest.Matchers.containsString("receipt URL")));
        }
}
