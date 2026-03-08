package com.neversion.panel.reservation.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.order.application.port.in.CreateOrderUseCase;
import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.domain.model.enums.OrderStatus;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("ValidateReservationService Unit Tests")
class ValidateReservationServiceTest {

    @Mock
    private ReservationRepositoryPort reservationRepositoryPort;

    @Mock
    private CreateOrderUseCase createOrderUseCase;

    private ValidateReservationService service;

    private static final UUID RESERVATION_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        service = new ValidateReservationService(reservationRepositoryPort, createOrderUseCase);
    }

    @Nested
    @DisplayName("validate()")
    class Validate {

        @Test
        @DisplayName("should validate reservation, transition to VALIDATED, and create order")
        void shouldValidateSuccessfully() {
            // Given
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.UPLOADED)
                    .receiptUrl("https://bank.com/receipt/abc123")
                    .total(new BigDecimal("19.98"))
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .build();

            Order createdOrder = Order.builder()
                    .id(UUID.randomUUID())
                    .reservationId(RESERVATION_ID)
                    .status(OrderStatus.VALIDATED)
                    .notes("Payment verified")
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));
            when(reservationRepositoryPort.update(any(Reservation.class)))
                    .thenAnswer(inv -> inv.getArgument(0));
            when(createOrderUseCase.createFromReservation(RESERVATION_ID, "Payment verified"))
                    .thenReturn(createdOrder);

            // When
            Reservation result = service.validate(RESERVATION_ID, "Payment verified");

            // Then
            assertThat(result.getStatus()).isEqualTo(ReservationStatus.VALIDATED);
            verify(createOrderUseCase).createFromReservation(RESERVATION_ID, "Payment verified");
            verify(reservationRepositoryPort).update(reservation);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when reservation not found")
        void shouldThrow_whenReservationNotFound() {
            UUID unknownId = UUID.randomUUID();
            when(reservationRepositoryPort.findById(unknownId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.validate(unknownId, null))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(unknownId.toString());
        }

        @Test
        @DisplayName("should throw BusinessRuleException when reservation is not UPLOADED")
        void shouldThrow_whenReservationIsNotUploaded() {
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.PENDING)
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));

            assertThatThrownBy(() -> service.validate(RESERVATION_ID, null))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("PENDING")
                    .hasMessageContaining("Expected: UPLOADED");

            verify(createOrderUseCase, never()).createFromReservation(any(), any());
            verify(reservationRepositoryPort, never()).update(any());
        }

        @Test
        @DisplayName("should pass null notes when no notes are provided")
        void shouldHandleNullNotes() {
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.UPLOADED)
                    .receiptUrl("https://bank.com/receipt/abc")
                    .total(new BigDecimal("9.99"))
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));
            when(reservationRepositoryPort.update(any(Reservation.class)))
                    .thenAnswer(inv -> inv.getArgument(0));
            when(createOrderUseCase.createFromReservation(RESERVATION_ID, null))
                    .thenReturn(Order.builder().build());

            // When
            Reservation result = service.validate(RESERVATION_ID, null);

            // Then
            assertThat(result.getStatus()).isEqualTo(ReservationStatus.VALIDATED);
            verify(createOrderUseCase).createFromReservation(RESERVATION_ID, null);
        }
    }
}
