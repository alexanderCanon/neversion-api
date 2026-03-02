package com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryRequest(

                @NotNull UUID productId,
                @NotNull @Positive BigDecimal priceAmount,
                @NotNull @Positive Integer durationDays,
                @NotBlank String accountType,
                @NotNull @Positive Integer stock) {
}
