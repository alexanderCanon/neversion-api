package com.neversion.panel.order.infrastructure.adapters.out.converter;

import com.neversion.panel.infrastructure.EnumConverter;
import com.neversion.panel.order.domain.model.enums.OrderStatus;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter extends EnumConverter<OrderStatus> {

    public OrderStatusConverter() {
        super(OrderStatus.class);
    }
}
