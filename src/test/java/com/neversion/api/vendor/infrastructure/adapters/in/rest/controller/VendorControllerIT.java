package com.neversion.api.vendor.infrastructure.adapters.in.rest.controller;

import com.neversion.api.BaseWebIntegrationTest;
import com.neversion.api.vendor.domain.model.Vendor;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("VendorController Integration Tests")
class VendorControllerIT extends BaseWebIntegrationTest {

    private static final String JWT_SECRET =
            "test-secret-key-for-testing-purposes-only-min-256-bits!!";
    private static final String VENDOR_EXTERNAL_ID = "auth|vendor-user-1";
    private static final UUID VENDOR_UUID = UUID.randomUUID();

    private String buildJwt(String role, String subject) throws Exception {
        JWSSigner signer = new MACSigner(JWT_SECRET.getBytes());
        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(subject)
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 3_600_000))
                .claim("app_metadata", Map.of("role", role))
                .build();
        SignedJWT jwt = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claims);
        jwt.sign(signer);
        return jwt.serialize();
    }

    private Vendor buildVendor() {
        return Vendor.builder()
                .id(1L)
                .uuid(VENDOR_UUID)
                .userId(10L)
                .storeName("Tienda Digital Pro")
                .logoUrl("https://example.com/logo.png")
                .bankDetails("{\"bank\":\"Banco Industrial\"}")
                .discountCfg("{\"min_items\":2,\"max_items\":4,\"round_to\":5,\"tiers\":[{\"count\":2,\"discount_pct\":10}]}")
                .rewardsCfg("{\"enabled\":true,\"earn_pct\":2.0}")
                .createdAt(Instant.now())
                .build();
    }

    @Nested
    @DisplayName("GET /api/v1/vendors/me")
    class GetCurrentVendorTests {

        @Test
        @DisplayName("should return 401 when no token is provided")
        void me_shouldReturn401_whenNoToken() throws Exception {
            mockMvc.perform(get("/api/v1/vendors/me"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("should return 403 when caller is a client")
        void me_shouldReturn403_whenClientRole() throws Exception {
            mockMvc.perform(get("/api/v1/vendors/me")
                            .header("Authorization", "Bearer " + buildJwt("client", "auth|client-user")))
                    .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("should return 200 with vendor profile when caller is vendor")
        void me_shouldReturn200_whenVendorRole() throws Exception {
            Vendor vendor = buildVendor();
            when(getCurrentVendorUseCase.getByCallerExternalId(VENDOR_EXTERNAL_ID)).thenReturn(vendor);

            mockMvc.perform(get("/api/v1/vendors/me")
                            .header("Authorization", "Bearer " + buildJwt("vendor", VENDOR_EXTERNAL_ID)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(VENDOR_UUID.toString()))
                    .andExpect(jsonPath("$.storeName").value("Tienda Digital Pro"))
                    .andExpect(jsonPath("$.logoUrl").value("https://example.com/logo.png"))
                    .andExpect(jsonPath("$.bankDetails").isNotEmpty())
                    .andExpect(jsonPath("$.discountCfg").isNotEmpty())
                    .andExpect(jsonPath("$.rewardsCfg").isNotEmpty());
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/vendors/discount-config")
    class UpdateDiscountConfigTests {

        @Test
        @DisplayName("should return 200 when updating discount config successfully")
        void updateDiscountConfig_shouldReturn200() throws Exception {
            String discountCfg = "{\"min_items\":2,\"max_items\":4,\"round_to\":5,\"tiers\":[{\"count\":2,\"discount_pct\":10}]}";
            when(updateDiscountConfigUseCase.updateDiscountConfig(eq(VENDOR_EXTERNAL_ID), eq(discountCfg)))
                    .thenReturn(discountCfg);

            mockMvc.perform(put("/api/v1/vendors/discount-config")
                            .header("Authorization", "Bearer " + buildJwt("vendor", VENDOR_EXTERNAL_ID))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"discountCfg\":\"" + discountCfg.replace("\"", "\\\"") + "\"}"))
                    .andExpect(status().isOk());
        }
    }
}
