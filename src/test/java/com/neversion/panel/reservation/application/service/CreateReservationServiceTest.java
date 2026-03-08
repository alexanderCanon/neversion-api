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

import com.neversion.panel.inventory.application.port.in.GetInventoryUseCase;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.reservation.application.port.in.ReservationItemCommand;
import com.neversion.panel.reservation.domain.model.Reservation;
import com.neversion.panel.reservation.domain.model.ReservationDetail;
import com.neversion.panel.reservation.domain.model.enums.ReservationStatus;
import com.neversion.panel.reservation.domain.port.out.ReservationRepositoryPort;
import com.neversion.panel.reservation.domain.service.ReservationPricingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateReservationService Unit Tests")
class CreateReservationServiceTest {

    @Mock
    private ReservationRepositoryPort reservationRepositoryPort;

    @Mock
    private GetInventoryUseCase getInventoryUseCase;

    private CreateReservationService service;

    private static final UUID USER_GUEST_ID = UUID.randomUUID();
    private static final UUID RESERVATION_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        ReservationPricingService pricingService = new ReservationPricingService();
        service = new CreateReservationService(reservationRepositoryPort, getInventoryUseCase, pricingService);
    }

    @Nested
    @DisplayName("create()")
    class Create {

        @Test
        @DisplayName("should create reservation and return it with details when inputs are valid")
        void shouldCreateReservationSuccessfully() {
            // Given
            List<ReservationItemCommand> items = List.of(
                    new ReservationItemCommand(1L, 2));

            Inventory inventory = Inventory.builder()
                    .id(1L)
                    .price(new BigDecimal("9.99"))
                    .build();

            Reservation savedReservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .userGuestId(USER_GUEST_ID)
                    .total(new BigDecimal("19.98"))
                    .status(ReservationStatus.PENDING)
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .createdAt(Instant.now())
                    .build();

            ReservationDetail savedDetail = new ReservationDetail(
                    UUID.randomUUID(), RESERVATION_ID, 1L, 2, new BigDecimal("9.99"), new BigDecimal("19.98"));

            when(reservationRepositoryPort.save(any(Reservation.class))).thenReturn(savedReservation);
            when(getInventoryUseCase.getById(1L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any(ReservationDetail.class))).thenReturn(savedDetail);

            // When
            Reservation result = service.create(USER_GUEST_ID, items);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(RESERVATION_ID);
            assertThat(result.getStatus()).isEqualTo(ReservationStatus.PENDING);
            assertThat(result.getTotal()).isEqualByComparingTo("19.98");
            assertThat(result.getDetails()).hasSize(1);
            assertThat(result.getDetails().get(0).unitPrice()).isEqualByComparingTo("9.99");
        }

        @Test
        @DisplayName("should compute total as sum of (qty × unitPrice) for all items")
        void shouldComputeTotalFromItems() {
            // Given
            List<ReservationItemCommand> items = List.of(
                    new ReservationItemCommand(1L, 2),
                    new ReservationItemCommand(2L, 1));

            Inventory inv1 = Inventory.builder().id(1L).price(new BigDecimal("10.00")).build();
            Inventory inv2 = Inventory.builder().id(2L).price(new BigDecimal("25.00")).build();

            when(getInventoryUseCase.getById(1L)).thenReturn(inv1);
            when(getInventoryUseCase.getById(2L)).thenReturn(inv2);
            when(reservationRepositoryPort.saveDetail(any())).thenAnswer(inv -> inv.getArgument(0));

            ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
            when(reservationRepositoryPort.save(captor.capture())).thenAnswer(inv -> {
                Reservation r = inv.getArgument(0);
                r.setId(RESERVATION_ID);
                r.setCreatedAt(Instant.now());
                return r;
            });

            // When
            service.create(USER_GUEST_ID, items);

            // Then: gross = (2 × 10.00) + (1 × 25.00) = 45.00
            // combo discount (2 items): 2% of 45.00 = 0.90
            // final total = 45.00 - 0.90 = 44.10
            Reservation captured = captor.getValue();
            assertThat(captured.getTotal()).isEqualByComparingTo("44.10");
        }

        @Test
        @DisplayName("should freeze the unit_price from inventory at reservation time")
        void shouldFreezeInventoryPrice() {
            // Given
            List<ReservationItemCommand> items = List.of(new ReservationItemCommand(5L, 1));

            Inventory inventory = Inventory.builder()
                    .id(5L)
                    .price(new BigDecimal("29.99"))
                    .build();

            Reservation savedReservation = Reservation.builder()
                    .id(RESERVATION_ID)
                    .userGuestId(USER_GUEST_ID)
                    .total(new BigDecimal("29.99"))
                    .status(ReservationStatus.PENDING)
                    .expirationDate(Instant.now().plus(60, ChronoUnit.MINUTES))
                    .build();

            when(reservationRepositoryPort.save(any(Reservation.class))).thenReturn(savedReservation);
            when(getInventoryUseCase.getById(5L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any(ReservationDetail.class)))
                    .thenAnswer(inv -> inv.getArgument(0));

            // When
            service.create(USER_GUEST_ID, items);

            // Then
            ArgumentCaptor<ReservationDetail> detailCaptor = ArgumentCaptor.forClass(ReservationDetail.class);
            verify(reservationRepositoryPort).saveDetail(detailCaptor.capture());
            assertThat(detailCaptor.getValue().unitPrice()).isEqualByComparingTo("29.99");
            assertThat(detailCaptor.getValue().inventoryId()).isEqualTo(5L);
        }

        @Test
        @DisplayName("should set expiration date to approximately 60 minutes from now")
        void shouldSetExpirationDateTo60Minutes() {
            // Given
            List<ReservationItemCommand> items = List.of(new ReservationItemCommand(2L, 1));
            Inventory inventory = Inventory.builder().id(2L).price(new BigDecimal("5.00")).build();

            when(getInventoryUseCase.getById(2L)).thenReturn(inventory);
            when(reservationRepositoryPort.saveDetail(any())).thenAnswer(inv -> inv.getArgument(0));

            ArgumentCaptor<Reservation> captor = ArgumentCaptor.forClass(Reservation.class);
            Instant before = Instant.now().plus(59, ChronoUnit.MINUTES);

            when(reservationRepositoryPort.save(captor.capture())).thenAnswer(inv -> {
                Reservation r = inv.getArgument(0);
                r.setId(RESERVATION_ID);
                return r;
            });

            // When
            service.create(USER_GUEST_ID, items);

            // Then
            Instant after = Instant.now().plus(61, ChronoUnit.MINUTES);
            Reservation captured = captor.getValue();
            assertThat(captured.getExpirationDate()).isAfter(before);
            assertThat(captured.getExpirationDate()).isBefore(after);
            assertThat(captured.getStatus()).isEqualTo(ReservationStatus.PENDING);
        }
    }
}
