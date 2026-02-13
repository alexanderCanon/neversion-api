package com.neversion.panel.orderdetail.domain.model;

import java.time.Instant;
import java.math.BigDecimal;

public record OrderDetail(
    Long id,
    Long orderId,
    Long serviceDetailId,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal subtotal,
    Instant purchaseDate
) {

}
