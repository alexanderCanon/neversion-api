package com.neversion.panel.order.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.order.domain.model.Order;
import com.neversion.panel.order.infrastructure.adapters.out.OrderEntity;

@Component
public class OrderPersistenceMapper {

    public Order toDomain(OrderEntity entity) {
        return entity != null ? Order.builder()
                .id(entity.getId())
                .reservationId(entity.getReservationId())
                .status(entity.getStatus())
                .notes(entity.getNotes())
                .createdAt(entity.getCreatedAt().toInstant())
                .build() : null;
    }

    public OrderEntity toEntity(Order domain) {
        return domain != null ? OrderEntity.builder()
                .id(domain.getId())
                .reservationId(domain.getReservationId())
                .status(domain.getStatus())
                .notes(domain.getNotes())
                .build() : null;
    }
}
