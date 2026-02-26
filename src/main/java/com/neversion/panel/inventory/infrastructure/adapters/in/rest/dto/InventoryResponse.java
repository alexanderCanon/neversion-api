package com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;

import com.neversion.panel.inventory.domain.model.enums.AccountType;

public record InventoryResponse(
        Long productId,
        BigDecimal price,
        String duration,
        AccountType accountType,
        Integer stock) {
}
