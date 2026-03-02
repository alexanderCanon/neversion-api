package com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.neversion.panel.shared.domain.model.enums.AccountType;

public record InventoryResponse(
                UUID productId,
                BigDecimal price,
                Integer durationDays,
                AccountType accountType,
                Integer stock) {
}
