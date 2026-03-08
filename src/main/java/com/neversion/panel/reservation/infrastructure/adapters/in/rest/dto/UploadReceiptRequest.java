package com.neversion.panel.reservation.infrastructure.adapters.in.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record UploadReceiptRequest(
        @NotBlank String receiptUrl) {
}
