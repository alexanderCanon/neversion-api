package com.neversion.panel.sservicedetail.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SserviceDetailRequest {
    @NotNull(message = "Service id is required")
    Integer serviceId;

    @NotNull(message = "Category id is required")
    Integer categoryId;

    BigDecimal priceIndividual;

    BigDecimal priceFamiliar;
}
