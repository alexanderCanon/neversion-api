package com.neversion.panel.plan.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlanRequest(

        @Positive Integer productId,
        @NotNull @Positive BigDecimal priceAmount,
        @NotBlank String duration,
        @NotBlank String accountType) {
}
