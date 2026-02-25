package com.neversion.panel.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetProductService Unit Tests")
class GetProductServiceTest {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    private GetProductService getProductService;

    @BeforeEach
    void setUp() {
        getProductService = new GetProductService(productRepositoryPort);
    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        @DisplayName("should return Product when it exists")
        void shouldReturnProduct_whenExists() {
            // Given
            Integer id = 1;
            Product expected = Product.builder()
                    .id(id)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.PLATAFORMA)
                    .build();

            when(productRepositoryPort.findById(id)).thenReturn(Optional.of(expected));

            // When
            Product result = getProductService.getById(id);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(id);
            assertThat(result.getName()).isEqualTo("Netflix");
            assertThat(result.getCategory()).isEqualTo(CategoryType.PLATAFORMA);
            verify(productRepositoryPort).findById(id);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when Product does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            // Given
            Integer id = 999;
            when(productRepositoryPort.findById(id)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getProductService.getById(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(String.valueOf(id));
        }
    }

    @Nested
    @DisplayName("getByName")
    class GetByName {

        @Test
        @DisplayName("should return Product when name exists")
        void shouldReturnProduct_whenExists() {
            // Given
            String name = "Spotify";
            Product expected = Product.builder()
                    .id(2)
                    .name(name)
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.SUSCRIPCION)
                    .build();

            when(productRepositoryPort.findByName(name)).thenReturn(Optional.of(expected));

            // When
            Product result = getProductService.getByName(name);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(name);
            assertThat(result.getCategory()).isEqualTo(CategoryType.SUSCRIPCION);
            verify(productRepositoryPort).findByName(name);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when name does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            // Given
            String name = "NonExistent";
            when(productRepositoryPort.findByName(name)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getProductService.getByName(name))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(name);
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("should return list of Products when they exist")
        void shouldReturnListOfProducts() {
            // Given
            Product netflix = Product.builder()
                    .id(1)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.PLATAFORMA)
                    .build();

            Product spotify = Product.builder()
                    .id(2)
                    .name("Spotify")
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.SUSCRIPCION)
                    .build();

            when(productRepositoryPort.findAll()).thenReturn(List.of(netflix, spotify));

            // When
            List<Product> result = getProductService.getAll();

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(Product::getName)
                    .containsExactly("Netflix", "Spotify");
            verify(productRepositoryPort).findAll();
        }

        @Test
        @DisplayName("should return empty list when no Products exist")
        void shouldReturnEmptyList_whenNoProductsExist() {
            // Given
            when(productRepositoryPort.findAll()).thenReturn(Collections.emptyList());

            // When
            List<Product> result = getProductService.getAll();

            // Then
            assertThat(result)
                    .isNotNull()
                    .isEmpty();
            verify(productRepositoryPort).findAll();
        }
    }
}
