package com.neversion.panel.inventory.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

import com.neversion.panel.inventory.domain.model.enums.AccountType;

public record Inventory(
    Long id,
    Long credentialsId,
    String credentialEmail,
    String credentialPass,
    String seller,
    BigDecimal priceSeller,
    Integer stock,
    AccountType accountType,
    LocalDate expirationDate,
    Boolean isActive,
    Instant createdAt
) {

}
