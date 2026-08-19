package com.neversion.api.vendor.infrastructure.adapters.in.rest.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.api.vendor.application.port.in.GetCurrentVendorUseCase;
import com.neversion.api.vendor.application.port.in.UpdateDiscountConfigUseCase;
import com.neversion.api.vendor.application.port.in.UpdateRewardsConfigUseCase;
import com.neversion.api.vendor.domain.model.Vendor;
import com.neversion.api.vendor.infrastructure.adapters.in.rest.dto.UpdateDiscountConfigRequest;
import com.neversion.api.vendor.infrastructure.adapters.in.rest.dto.UpdateRewardsConfigRequest;
import com.neversion.api.vendor.infrastructure.adapters.in.rest.dto.VendorProfileResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST controller for vendor management operations (BR-13 v2).
 * <p>
 * All endpoints require VENDOR or SUPER_ADMIN role (see VendorSecurityConfig).
 */
@RestController
@RequestMapping(value = "/api/v1/vendors", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vendors", description = "Vendor management and configuration")
public class VendorController {

    private final GetCurrentVendorUseCase getCurrentVendorUseCase;
    private final UpdateDiscountConfigUseCase updateDiscountConfigUseCase;
    private final UpdateRewardsConfigUseCase updateRewardsConfigUseCase;

    public VendorController(
            GetCurrentVendorUseCase getCurrentVendorUseCase,
            UpdateDiscountConfigUseCase updateDiscountConfigUseCase,
            UpdateRewardsConfigUseCase updateRewardsConfigUseCase) {
        this.getCurrentVendorUseCase = getCurrentVendorUseCase;
        this.updateDiscountConfigUseCase = updateDiscountConfigUseCase;
        this.updateRewardsConfigUseCase = updateRewardsConfigUseCase;
    }

    /**
     * Retrieves the profile, branding, and configuration of the authenticated vendor.
     *
     * @param jwt authenticated caller's JWT
     * @return vendor profile details
     */
    @GetMapping("/me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Get authenticated vendor profile",
            description = "Returns profile details, branding and configurations for the authenticated vendor."
    )
    @ApiResponse(responseCode = "200", description = "Vendor profile found")
    @ApiResponse(responseCode = "401", description = "No valid JWT provided")
    @ApiResponse(responseCode = "403", description = "Not a vendor or super admin")
    @ApiResponse(responseCode = "404", description = "Vendor record not found")
    public ResponseEntity<VendorProfileResponse> me(@AuthenticationPrincipal Jwt jwt) {
        Vendor vendor = getCurrentVendorUseCase.getByCallerExternalId(jwt.getSubject());
        return ResponseEntity.ok(new VendorProfileResponse(
                vendor.getUuid(),
                vendor.getStoreName(),
                vendor.getLogoUrl(),
                vendor.getBankDetails(),
                vendor.getDiscountCfg(),
                vendor.getRewardsCfg(),
                vendor.getCreatedAt()
        ));
    }

    /**
     * Updates the authenticated vendor's discount configuration (BR-13 v2).
     * <p>
     * The caller is identified from the JWT subject. The request body must contain
     * a valid JSON string conforming to the BR-13 discount_cfg structure.
     *
     * @param request contains the discount_cfg JSON
     * @param jwt     authenticated caller's JWT
     * @return the persisted discount_cfg JSON
     */
    @PutMapping("/discount-config")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Update vendor discount configuration (BR-13 v2)",
            description = "Updates the discount_cfg JSON for the authenticated vendor. "
                    + "Validates structure: min_items >= 2, max_items <= 4, consecutive tiers, "
                    + "discount_pct 0-100, round_to > 0.")
    @ApiResponse(responseCode = "200", description = "Discount configuration updated")
    @ApiResponse(responseCode = "400", description = "Invalid JSON structure or validation error")
    @ApiResponse(responseCode = "403", description = "Not a vendor or super admin")
    public ResponseEntity<String> updateDiscountConfig(
            @Valid @RequestBody UpdateDiscountConfigRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String updated = updateDiscountConfigUseCase.updateDiscountConfig(
                jwt.getSubject(), request.discountCfg());
        return ResponseEntity.ok(updated);
    }

    /**
     * Updates the authenticated vendor's loyalty points (rewards) configuration.
     * <p>
     * The caller is identified from the JWT subject. The request body must contain
     * a valid JSON string: { "enabled": true, "earn_pct": 2.0 }.
     *
     * @param request contains the rewards_cfg JSON
     * @param jwt     authenticated caller's JWT
     * @return the persisted rewards_cfg JSON
     */
    @PutMapping("/rewards-config")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(
            summary = "Update vendor rewards (loyalty points) configuration",
            description = "Updates the rewards_cfg JSON for the authenticated vendor. "
                    + "Validates structure: enabled (boolean), earn_pct (0-100).")
    @ApiResponse(responseCode = "200", description = "Rewards configuration updated")
    @ApiResponse(responseCode = "400", description = "Invalid JSON structure or validation error")
    @ApiResponse(responseCode = "403", description = "Not a vendor or super admin")
    public ResponseEntity<String> updateRewardsConfig(
            @Valid @RequestBody UpdateRewardsConfigRequest request,
            @AuthenticationPrincipal Jwt jwt) {

        String updated = updateRewardsConfigUseCase.updateRewardsConfig(
                jwt.getSubject(), request.rewardsCfg());
        return ResponseEntity.ok(updated);
    }
}
