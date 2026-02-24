package com.neversion.panel.sserviceitem.domain.model;

import java.math.BigDecimal;

public record SservicePrice(BigDecimal amount) {

    public SservicePrice {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
}
