package com.neversion.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration test for the {@code SecurityFilterChain}.
 * <p>
 * Boots the full Spring context with a real PostgreSQL container and verifies
 * that the security rules are enforced at the HTTP layer:
 * <ul>
 *   <li>Public endpoints return 200 without any token</li>
 *   <li>Protected endpoints return 401 without a token</li>
 *   <li>Protected endpoints return 401 with an invalid/malformed JWT</li>
 * </ul>
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("SecurityFilterChain IT — endpoint protection rules")
class SecurityFilterChainIT extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // ── Public endpoints (no token needed) ─────────────────────────────

    @Nested
    @DisplayName("Public endpoints")
    class PublicEndpoints {

        @Test
        @DisplayName("GET /api/v1/products - should return 200 without token (public catalog)")
        void products_shouldBePublic() throws Exception {
            mockMvc.perform(get("/api/v1/products"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("GET /actuator/health - should return 200 without token")
        void actuatorHealth_shouldBePublic() throws Exception {
            mockMvc.perform(get("/actuator/health"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("GET /v3/api-docs - should return 200 without token (OpenAPI)")
        void apiDocs_shouldBePublic() throws Exception {
            mockMvc.perform(get("/v3/api-docs"))
                    .andExpect(status().isOk());
        }
    }

    // ── Protected endpoints (token required) ───────────────────────────

    @Nested
    @DisplayName("Protected endpoints — no token")
    class ProtectedEndpointsNoToken {

        @Test
        @DisplayName("POST /api/v1/products - should return 401 without token")
        void createProduct_shouldReturn401_withoutToken() throws Exception {
            mockMvc.perform(post("/api/v1/products")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "Netflix",
                                        "description": "Streaming",
                                        "category": "STREAMING",
                                        "items": [{ "priceAmount": 9.99, "durationDays": 30, "accountType": "individual" }]
                                    }
                                    """))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("GET /api/v1/accounts - should return 401 without token (admin-only)")
        void getAccounts_shouldReturn401_withoutToken() throws Exception {
            mockMvc.perform(get("/api/v1/accounts"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("GET /api/v1/reservations - should return 401 without token (admin-only list)")
        void listReservations_shouldReturn401_withoutToken() throws Exception {
            mockMvc.perform(get("/api/v1/reservations"))
                    .andExpect(status().isUnauthorized());
        }
    }

    // ── Invalid JWT ────────────────────────────────────────────────────

    @Nested
    @DisplayName("Protected endpoints — invalid JWT")
    class ProtectedEndpointsInvalidJwt {

        @Test
        @DisplayName("POST /api/v1/products - should return 401 with malformed JWT")
        void createProduct_shouldReturn401_withMalformedJwt() throws Exception {
            mockMvc.perform(post("/api/v1/products")
                            .header("Authorization", "Bearer this.is.not.a.valid.jwt")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "Netflix",
                                        "description": "Streaming",
                                        "category": "STREAMING",
                                        "items": [{ "priceAmount": 9.99, "durationDays": 30, "accountType": "individual" }]
                                    }
                                    """))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @DisplayName("POST /api/v1/products - should return 401 with expired/invalid signature JWT")
        void createProduct_shouldReturn401_withInvalidSignatureJwt() throws Exception {
            // This is a structurally valid JWT but signed with a different secret
            String invalidJwt = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9"
                    + ".eyJzdWIiOiIxMjM0NTY3ODkwIiwicm9sZSI6ImFkbWluIiwiaWF0IjoxNTE2MjM5MDIyfQ"
                    + ".SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

            mockMvc.perform(post("/api/v1/products")
                            .header("Authorization", "Bearer " + invalidJwt)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "name": "Netflix",
                                        "description": "Streaming",
                                        "category": "STREAMING",
                                        "items": [{ "priceAmount": 9.99, "durationDays": 30, "accountType": "individual" }]
                                    }
                                    """))
                    .andExpect(status().isUnauthorized());
        }
    }
}
