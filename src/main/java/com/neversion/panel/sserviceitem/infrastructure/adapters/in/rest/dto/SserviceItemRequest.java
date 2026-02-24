package com.neversion.panel.sserviceitem.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SserviceItemRequest(

                @Positive Integer serviceId,
                @NotNull @Positive BigDecimal priceAmount,
                @NotBlank String duration,
                @NotBlank String accountType) {
}
