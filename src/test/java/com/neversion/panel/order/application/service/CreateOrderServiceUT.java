package com.neversion.panel.order.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.domain.model.enums.OrderStatus;
import com.neversion.panel.order.domain.port.out.OrderRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateOrderService unit tests")
class CreateOrderServiceUT {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    private CreateOrderService createOrderService;

    @BeforeEach
    void setUp() {
        createOrderService = new CreateOrderService(orderRepositoryPort);
    }

    @Nested
    @DisplayName("createFromReservation")
    class CreateFromReservation {

        @Test
        @DisplayName("createFromReservation - should create order with VALIDATED status")
        void createFromReservation_shouldCreateOrderWithValidatedStatus() {
            // Given
            UUID reservationId = UUID.randomUUID();
            String notes = "Payment verified";

            Order savedOrder = Order.builder()
                    .id(UUID.randomUUID())
                    .reservationId(reservationId)
                    .status(OrderStatus.VALIDATED)
                    .notes(notes)
                    .build();
            when(orderRepositoryPort.save(any(Order.class))).thenReturn(savedOrder);

            // When
            Order result = createOrderService.createFromReservation(reservationId, notes);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getStatus()).isEqualTo(OrderStatus.VALIDATED);
            assertThat(result.getReservationId()).isEqualTo(reservationId);
            assertThat(result.getNotes()).isEqualTo(notes);
        }

        @Test
        @DisplayName("createFromReservation - should delegate save to repository with correct order")
        void createFromReservation_shouldDelegateSaveToRepository() {
            // Given
            UUID reservationId = UUID.randomUUID();
            when(orderRepositoryPort.save(any(Order.class))).thenAnswer(inv -> inv.getArgument(0));

            // When
            createOrderService.createFromReservation(reservationId, null);

            // Then
            ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
            verify(orderRepositoryPort).save(captor.capture());
            Order captured = captor.getValue();
            assertThat(captured.getReservationId()).isEqualTo(reservationId);
            assertThat(captured.getStatus()).isEqualTo(OrderStatus.VALIDATED);
            assertThat(captured.getNotes()).isNull();
        }
    }
}
