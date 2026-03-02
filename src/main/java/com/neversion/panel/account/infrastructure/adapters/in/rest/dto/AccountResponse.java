package com.neversion.panel.account.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.shared.domain.model.enums.AccountStatus;
import com.neversion.panel.shared.domain.model.enums.AccountType;

public record AccountResponse(
                UUID id,
                String email,
                String pass,
                UUID productId,
                String seller,
                BigDecimal priceSeller,
                AccountType accountType,
                AccountStatus status,
                LocalDate expirationDate,
                Boolean isActive) {

}
