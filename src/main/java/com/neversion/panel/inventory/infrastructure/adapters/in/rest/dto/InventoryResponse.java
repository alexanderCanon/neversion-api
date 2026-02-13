package com.neversion.panel.inventory.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.neversion.panel.inventory.domain.model.enums.AccountType;

public record InventoryResponse(
    Long id,
    String credentialEmail,
    String credentialPass,
    String seller,
    BigDecimal priceSeller,
    AccountType accountType,
    LocalDate expirationDate,
    Boolean isActive
) {

}
