package com.neversion.panel.product.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.product.application.port.in.GetProductUseCase;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.infrastructure.adapters.in.rest.controller.ProductGetController;
import com.neversion.panel.product.infrastructure.adapters.in.rest.dto.ProductResponse;
import com.neversion.panel.product.infrastructure.adapters.in.rest.mapper.ProductMapper;

@WebMvcTest(ProductGetController.class)
class ProductGetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetProductUseCase getProductUseCase;

    @MockitoBean
    private ProductMapper productMapper;

    private Product buildProduct(UUID id, String name) {
        return Product.builder()
                .id(id)
                .name(name)
                .description("Test description")
                .category(CategoryType.STREAMING)
                .build();
    }

    private ProductResponse buildResponse(UUID id, String name) {
        return new ProductResponse(id, name, "Test description", null, String.valueOf(CategoryType.STREAMING));
    }

    // -- GET by ID --

    @Nested
    @DisplayName("GET /api/v1/products/{id}")
    class GetById {

        @Test
        @DisplayName("→ 200 when service exists")
        void getById_shouldReturn200() throws Exception {
            UUID id = UUID.randomUUID();
            Product product = buildProduct(id, "Netflix");
            ProductResponse response = buildResponse(id, "Netflix");

            when(getProductUseCase.getById(id)).thenReturn(product);
            when(productMapper.toResponse(product)).thenReturn(response);

            mockMvc.perform(get("/api/v1/products/{id}", id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()))
                    .andExpect(jsonPath("$.name").value("Netflix"));
        }

        @Test
        @DisplayName("→ 404 when service does not exist")
        void getById_shouldReturn404_whenNotExists() throws Exception {
            UUID id = UUID.randomUUID();
            when(getProductUseCase.getById(any(UUID.class)))
                    .thenThrow(new ResourceNotFoundException("Product with id " + id + " not found"));

            mockMvc.perform(get("/api/v1/products/{id}", id))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404));
        }
    }

    // -- GET by name --

    @Nested
    @DisplayName("GET /api/v1/products?name=")
    class GetByName {

        @Test
        @DisplayName("→ 200 when service exists by name")
        void getByName_shouldReturn200() throws Exception {
            UUID id = UUID.randomUUID();
            Product product = buildProduct(id, "Netflix");
            ProductResponse response = buildResponse(id, "Netflix");

            when(getProductUseCase.getByName("Netflix")).thenReturn(product);
            when(productMapper.toResponse(product)).thenReturn(response);

            mockMvc.perform(get("/api/v1/products").param("name", "Netflix"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(id.toString()))
                    .andExpect(jsonPath("$.name").value("Netflix"));
        }

        @Test
        @DisplayName("→ 404 when service does not exist by name")
        void getByName_shouldReturn404_whenNotExists() throws Exception {
            when(getProductUseCase.getByName(anyString()))
                    .thenThrow(new ResourceNotFoundException("Product with name Unknown not found"));

            mockMvc.perform(get("/api/v1/products").param("name", "Unknown"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404));
        }
    }

    // -- GET all --

    @Nested
    @DisplayName("GET /api/v1/products")
    class GetAll {

        @Test
        @DisplayName("→ 200 with list of services")
        void getAll_shouldReturn200_withList() throws Exception {
            UUID id1 = UUID.randomUUID();
            UUID id2 = UUID.randomUUID();
            Product s1 = buildProduct(id1, "Netflix");
            Product s2 = buildProduct(id2, "Spotify");
            ProductResponse r1 = buildResponse(id1, "Netflix");
            ProductResponse r2 = buildResponse(id2, "Spotify");

            when(getProductUseCase.getAll()).thenReturn(List.of(s1, s2));
            when(productMapper.toResponse(s1)).thenReturn(r1);
            when(productMapper.toResponse(s2)).thenReturn(r2);

            mockMvc.perform(get("/api/v1/products"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Netflix"))
                    .andExpect(jsonPath("$[1].name").value("Spotify"));
        }

        @Test
        @DisplayName("→ 200 with empty list when no services exist")
        void getAll_shouldReturn200_withEmptyList() throws Exception {
            when(getProductUseCase.getAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/v1/products"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));
        }
    }
}
