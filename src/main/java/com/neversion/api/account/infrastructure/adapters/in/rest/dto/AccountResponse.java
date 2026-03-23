package com.neversion.api.account.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;

@Builder
public record AccountResponse(
        UUID id,
        String email,
        String pass,
        Long inventoryId,
        String seller,
        BigDecimal priceSeller,
        String accountType,
        String status,
        LocalDate expirationDate) {

}
