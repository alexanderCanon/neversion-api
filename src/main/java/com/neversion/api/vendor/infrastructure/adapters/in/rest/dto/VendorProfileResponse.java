package com.neversion.api.vendor.infrastructure.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

public record VendorProfileResponse(

        @Schema(description = "Public UUID of the vendor")
        UUID id,

        @Schema(description = "Display name of the vendor's store")
        String storeName,

        @Schema(description = "URL to the vendor's logo image")
        String logoUrl,

        @Schema(description = "Bank details JSON string")
        String bankDetails,

        @Schema(description = "Discount tier configuration JSON string")
        String discountCfg,

        @Schema(description = "Rewards configuration JSON string")
        String rewardsCfg,

        @Schema(description = "Creation timestamp")
        Instant createdAt
) {
}
