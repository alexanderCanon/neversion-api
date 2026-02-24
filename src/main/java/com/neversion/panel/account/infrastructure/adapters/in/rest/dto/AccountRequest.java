package com.neversion.panel.account.infrastructure.adapters.in.rest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.neversion.panel.account.domain.model.enums.AccountType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email;

    @NotBlank(message = "Password is required")
    String pass;

    @NotNull(message = "Service id is required")
    Integer serviceId;

    @NotBlank(message = "Seller is required")
    @Size(max = 255, message = "Seller must not exceed 255 characters")
    String seller;

    @NotNull(message = "Price seller is required")
    @DecimalMin(value = "0.01", message = "Price seller must be greater than 0")
    BigDecimal priceSeller;

    Integer stock;

    @NotNull(message = "Account type is required")
    AccountType accountType;

    @NotNull(message = "Expiration date is required")
    LocalDate expirationDate;
}
