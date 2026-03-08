package com.neversion.panel.order.application.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.order.application.port.in.GetOrderUseCase;
import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.domain.port.out.OrderRepositoryPort;

@Service
public class GetOrderService implements GetOrderUseCase {

    private final OrderRepositoryPort orderRepositoryPort;

    public GetOrderService(OrderRepositoryPort orderRepositoryPort) {
        this.orderRepositoryPort = orderRepositoryPort;
    }

    @Override
    public Optional<Order> getById(UUID id) {
        return orderRepositoryPort.findById(id);
    }

    @Override
    public Optional<Order> getByReservationId(UUID reservationId) {
        return orderRepositoryPort.findByReservationId(reservationId);
    }
}
