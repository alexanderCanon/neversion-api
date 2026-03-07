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
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("UploadReceiptService Unit Tests")
class UploadReceiptServiceTest {

    @Mock
    private ReservationRepositoryPort reservationRepositoryPort;

    private UploadReceiptService service;

    private static final UUID RESERVATION_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        service = new UploadReceiptService(reservationRepositoryPort);
    }

    @Nested
    @DisplayName("uploadReceipt()")
    class UploadReceipt {

        @Test
        @DisplayName("should upload receipt and transition status to UPLOADED")
        void shouldUploadReceiptSuccessfully() {
            // Given
            String receiptUrl = "https://bank.com/receipt/abc123";
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.PENDING)
                    .total(new BigDecimal("19.98"))
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));
            when(reservationRepositoryPort.existsByReceiptUrl(receiptUrl))
                    .thenReturn(false);
            when(reservationRepositoryPort.update(any(Reservation.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            // When
            Reservation result = service.uploadReceipt(RESERVATION_ID, receiptUrl);

            // Then
            assertThat(result.getStatus()).isEqualTo(ReservationStatus.UPLOADED);
            assertThat(result.getReceiptUrl()).isEqualTo(receiptUrl);
            verify(reservationRepositoryPort).update(reservation);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when reservation not found")
        void shouldThrow_whenReservationNotFound() {
            UUID unknownId = UUID.randomUUID();
            when(reservationRepositoryPort.findById(unknownId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.uploadReceipt(unknownId, "https://bank.com/receipt/xyz"))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(unknownId.toString());
        }

        @Test
        @DisplayName("should throw BusinessRuleException when reservation is not PENDING")
        void shouldThrow_whenReservationIsNotPending() {
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.UPLOADED)
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));

            assertThatThrownBy(() -> service.uploadReceipt(RESERVATION_ID, "https://bank.com/receipt/xyz"))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("UPLOADED");

            verify(reservationRepositoryPort, never()).update(any());
        }

        @Test
        @DisplayName("should throw BusinessRuleException when receiptUrl is already used (BR-05)")
        void shouldThrow_whenReceiptUrlIsDuplicate() {
            String duplicateUrl = "https://bank.com/receipt/duplicate";
            Reservation reservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .status(ReservationStatus.PENDING)
                    .build();

            when(reservationRepositoryPort.findById(RESERVATION_ID))
                    .thenReturn(Optional.of(reservation));
            when(reservationRepositoryPort.existsByReceiptUrl(duplicateUrl))
                    .thenReturn(true);

            assertThatThrownBy(() -> service.uploadReceipt(RESERVATION_ID, duplicateUrl))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("receipt URL");

            verify(reservationRepositoryPort, never()).update(any());
        }
    }
}
