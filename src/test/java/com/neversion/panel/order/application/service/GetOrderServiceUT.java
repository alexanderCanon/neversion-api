package com.neversion.panel.order.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.domain.model.enums.OrderStatus;
import com.neversion.panel.order.domain.port.out.OrderRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetOrderService Unit Tests")
class GetOrderServiceUT {

    @Mock
    private OrderRepositoryPort orderRepositoryPort;

    private GetOrderService getOrderService;

    @BeforeEach
    void setUp() {
        getOrderService = new GetOrderService(orderRepositoryPort);
    }

    private Order buildOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .reservationId(UUID.randomUUID())
                .status(OrderStatus.VALIDATED)
                .notes("Test order")
                .createdAt(Instant.now())
                .build();
    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        @DisplayName("should return order when found")
        void shouldReturnOrder() {
            Order order = buildOrder();
            when(orderRepositoryPort.findById(order.getId()))
                    .thenReturn(Optional.of(order));

            Optional<Order> result = getOrderService.getById(order.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getId()).isEqualTo(order.getId());
        }

        @Test
        @DisplayName("should return empty when not found")
        void shouldReturnEmpty() {
            UUID id = UUID.randomUUID();
            when(orderRepositoryPort.findById(id)).thenReturn(Optional.empty());

            Optional<Order> result = getOrderService.getById(id);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("getByReservationId")
    class GetByReservationId {

        @Test
        @DisplayName("should return order when found by reservation ID")
        void shouldReturnOrderByReservation() {
            Order order = buildOrder();
            when(orderRepositoryPort.findByReservationId(order.getReservationId()))
                    .thenReturn(Optional.of(order));

            Optional<Order> result = getOrderService.getByReservationId(
                    order.getReservationId());

            assertThat(result).isPresent();
            assertThat(result.get().getReservationId()).isEqualTo(order.getReservationId());
        }
    }
}
