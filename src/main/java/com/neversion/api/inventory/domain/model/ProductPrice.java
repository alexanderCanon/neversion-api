package com.neversion.api.inventory.domain.model;

import java.math.BigDecimal;

public record ProductPrice(BigDecimal amount) {

    public ProductPrice {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
}
