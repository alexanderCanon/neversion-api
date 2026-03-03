package com.neversion.panel.reservation.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.reservation.application.port.in.ReservationItemCommand;
import com.neversion.panel.reservation.domain.model.GuestUser;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateReservationService Unit Tests")
class CreateReservationServiceTest {

    @Mock
    private ReservationRepositoryPort reservationRepositoryPort;

    @Mock
    private GetInventoryUseCase getInventoryUseCase;

    private CreateReservationService service;

    private static final UUID GUEST_ID = UUID.randomUUID();
    private static final UUID RESERVATION_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        service = new CreateReservationService(reservationRepositoryPort, getInventoryUseCase);
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("should create reservation and return it with details when inputs are valid")
        void shouldCreateReservationSuccessfully() {
            // Given
            GuestUser guest = new GuestUser(null, "John Doe", "john@example.com", "555-0100");
            List<ReservationItemCommand> items = List.of(
                    new ReservationItemCommand(1L, 2));
            String proofUrl = "https://bank.com/receipt/abc123";

            GuestUser savedGuest = new GuestUser(GUEST_ID, "John Doe", "john@example.com", "555-0100");

            Inventory inventory = Inventory.builder()
                    .id(1L)
                    .price(new BigDecimal("9.99"))
                    .build();

            Reservation savedReservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .userGuestId(GUEST_ID)
                    .proofUrl(proofUrl)
                    .status(ReservationStatus.PENDING)
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .createdAt(Instant.now())
                    .build();

            ReservationDetail savedDetail = new ReservationDetail(
                    UUID.randomUUID(), RESERVATION_ID, 1L, 2, new BigDecimal("9.99"));

            when(reservationRepositoryPort.existsByProofUrl(proofUrl)).thenReturn(false);
            when(reservationRepositoryPort.findOrCreateGuest(guest)).thenReturn(savedGuest);
            when(reservationRepositoryPort.save(any(Reservation.class))).thenReturn(savedReservation);
            when(getInventoryUseCase.getById(1L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any(ReservationDetail.class))).thenReturn(savedDetail);

            // When
            Reservation result = service.create(guest, items, proofUrl);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(RESERVATION_ID);
            assertThat(result.getStatus()).isEqualTo(ReservationStatus.PENDING);
            assertThat(result.getDetails()).hasSize(1);
            assertThat(result.getDetails().get(0).unitPrice()).isEqualByComparingTo("9.99");
        }

        @Test
        @DisplayName("should throw BusinessRuleException when proofUrl is already in use")
        void shouldThrowException_whenProofUrlAlreadyExists() {
            // Given
            GuestUser guest = new GuestUser(null, "Jane", "jane@example.com", "555-0200");
            List<ReservationItemCommand> items = List.of(new ReservationItemCommand(1L, 1));
            String duplicateProofUrl = "https://bank.com/receipt/duplicate";

            when(reservationRepositoryPort.existsByProofUrl(duplicateProofUrl)).thenReturn(true);

            // When & Then
            assertThatThrownBy(() -> service.create(guest, items, duplicateProofUrl))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("proof_url");

            verify(reservationRepositoryPort, never()).findOrCreateGuest(any());
            verify(reservationRepositoryPort, never()).save(any());
        }

        @Test
        @DisplayName("should freeze the unit_price from inventory at reservation time")
        void shouldFreezeInventoryPrice() {
            // Given
            GuestUser guest = new GuestUser(null, "Bob", "bob@example.com", "555-0300");
            List<ReservationItemCommand> items = List.of(new ReservationItemCommand(5L, 1));
            String proofUrl = "https://bank.com/receipt/xyz789";

            GuestUser savedGuest = new GuestUser(GUEST_ID, "Bob", "bob@example.com", "555-0300");

            Inventory inventory = Inventory.builder()
                    .id(5L)
                    .price(new BigDecimal("29.99"))
                    .build();

            Reservation savedReservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .userGuestId(GUEST_ID)
                    .proofUrl(proofUrl)
                    .status(ReservationStatus.PENDING)
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .build();

            when(reservationRepositoryPort.existsByProofUrl(proofUrl)).thenReturn(false);
            when(reservationRepositoryPort.findOrCreateGuest(guest)).thenReturn(savedGuest);
            when(reservationRepositoryPort.save(any(Reservation.class))).thenReturn(savedReservation);
            when(getInventoryUseCase.getById(5L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any(ReservationDetail.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            // When
            service.create(guest, items, proofUrl);

            // Then: capture what was sent to saveDetail and verify price is from inventory
            ArgumentCaptor<ReservationDetail> detailCaptor = ArgumentCaptor.forClass(ReservationDetail.class);
            verify(reservationRepositoryPort).saveDetail(detailCaptor.capture());
            assertThat(detailCaptor.getValue().unitPrice()).isEqualByComparingTo("29.99");
            assertThat(detailCaptor.getValue().inventoryId()).isEqualTo(5L);
        }

        @Test
        @DisplayName("should set expiration date to approximately 60 minutes from now")
        void shouldSetExpirationDateTo60Minutes() {
            // Given
            GuestUser guest = new GuestUser(null, "Alice", "alice@example.com", "555-0400");
            List<ReservationItemCommand> items = List.of(new ReservationItemCommand(2L, 1));
            String proofUrl = "https://bank.com/receipt/timer123";

            GuestUser savedGuest = new GuestUser(GUEST_ID, "Alice", "alice@example.com", "555-0400");
            Inventory inventory = Inventory.builder().id(2L).price(new BigDecimal("5.00")).build();

            when(reservationRepositoryPort.existsByProofUrl(proofUrl)).thenReturn(false);
            when(reservationRepositoryPort.findOrCreateGuest(guest)).thenReturn(savedGuest);
            when(getInventoryUseCase.getById(2L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any())).thenAnswer(inv -> inv.getArgument(0));

            // Capture the reservation to check expiration
            ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
            Instant before = Instant.now().plus(59, ChronoUnit.MINUTES);

            when(reservationRepositoryPort.save(captor.capture())).thenAnswer(inv -> {
                Reservation r = inv.getArgument(0);
                r.setId(RESERVATION_ID);
                return r;
            });

            // When
            service.create(guest, items, proofUrl);

            // Then
            Instant after = Instant.now().plus(61, ChronoUnit.MINUTES);
            Reservation captured = captor.getValue();
            assertThat(captured.getExpirationDate()).isAfter(before);
            assertThat(captured.getExpirationDate()).isBefore(after);
            assertThat(captured.getStatus()).isEqualTo(ReservationStatus.PENDING);
        }
    }
}
