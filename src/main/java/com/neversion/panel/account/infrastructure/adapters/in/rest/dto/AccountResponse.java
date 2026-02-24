package com.neversion.panel.account.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.neversion.panel.account.domain.model.enums.AccountType;

public record AccountResponse(
        Long id,
        String email,
        String pass,
        Integer serviceId,
        String seller,
        BigDecimal priceSeller,
        Integer stock,
        AccountType accountType,
        LocalDate expirationDate,
        Boolean isActive) {

}
