package com.neversion.panel.product.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neversion.panel.config.SecurityConfig;
import com.neversion.panel.product.application.port.in.CreateProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.infrastructure.adapters.in.rest.controller.ProductPostController;
import com.neversion.panel.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

@WebMvcTest(ProductPostController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("ProductPostController Slicing Tests")
class ProductPostControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateProductUseCase createProductUseCase;

    @MockitoBean
    private ProductMapper productMapper;

    // -- Happy path --

    @Test
    @DisplayName("POST /api/v1/products → 201 CREATED when request is valid")
    void createProduct_shouldReturn201() throws Exception {
        UUID generatedId = UUID.randomUUID();

        // Given
        String requestBody = """
                {
                    "name": "Netflix",
                    "description": "Streaming service",
                    "imageUrl": "https://img.com/netflix.png",
                    "category": "STREAMING",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "durationDays": 30,
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        Product domainInput = Product.builder()
                .name("Netflix")
                .description("Streaming service")
                .imageUrl("https://img.com/netflix.png")
                .category(CategoryType.STREAMING)
                .build();

        Product domainOutput = Product.builder()
                .id(generatedId)
                .name("Netflix")
                .description("Streaming service")
                .imageUrl("https://img.com/netflix.png")
                .category(CategoryType.STREAMING)
                .build();

        when(productMapper.toDomain(any())).thenReturn(domainInput);
        when(createProductUseCase.create(any(Product.class))).thenReturn(domainOutput);

        // When & Then
        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated());
    }

    // -- Validation tests --

    @Test
    @DisplayName("POST /api/v1/products → 400 when name is blank")
    void createProduct_shouldReturn400_whenNameIsBlank() throws Exception {
        String requestBody = """
                {
                    "name": "",
                    "description": "Streaming service",
                    "category": "STREAMING",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "durationDays": 30,
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/products → 400 when name is too short")
    void createProduct_shouldReturn400_whenNameIsTooShort() throws Exception {
        String requestBody = """
                {
                    "name": "ab",
                    "description": "Streaming service",
                    "category": "STREAMING",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "durationDays": 30,
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/products → 400 when items list is empty")
    void createProduct_shouldReturn400_whenItemsAreEmpty() throws Exception {
        String requestBody = """
                {
                    "name": "Netflix",
                    "description": "Streaming service",
                    "category": "STREAMING",
                    "items": []
                }
                """;

        mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
