package com.neversion.panel.order.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.infrastructure.adapters.in.rest.dto.OrderResponse;

@Component
public class OrderRestMapper {

    public OrderResponse toResponse(Order order) {
        return order != null ? OrderResponse.builder()
                .id(order.getId())
                .reservationId(order.getReservationId())
                .status(order.getStatus())
                .notes(order.getNotes())
                .createdAt(order.getCreatedAt())
                .build() : null;
    }
}
