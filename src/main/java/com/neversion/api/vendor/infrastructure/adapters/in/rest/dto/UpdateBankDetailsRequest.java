package com.neversion.api.vendor.infrastructure.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request payload for updating vendor bank / payment methods configuration.
 */
public record UpdateBankDetailsRequest(
        @NotBlank(message = "bankDetails must not be blank")
        @Schema(
                description = "Bank details and payment accounts configuration as a JSON array string",
                example = "[{\"bank\":\"Banrural\",\"accountNumber\":\"4426313592\",\"accountType\":\"Ahorro en Quetzales\",\"holder\":\"Alexander Canon\"}]"
        )
        String bankDetails
) {}
