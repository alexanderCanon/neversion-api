package com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record InventoryResponse(
                Long id,
                UUID productId,
                BigDecimal price,
                Integer durationDays,
                String accountType,
                Integer stock,
                Integer maxProfiles) {
}
