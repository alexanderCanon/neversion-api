package com.neversion.panel.account.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.neversion.panel.account.domain.model.enums.AccountType;

public record Account(
        Long id,
        String email,
        String pass,
        Integer serviceId,
        String seller,
        BigDecimal priceSeller,
        Integer stock,
        AccountType accountType,
        LocalDate expirationDate,
        Boolean isActive,
        Instant createdAt) {

}
