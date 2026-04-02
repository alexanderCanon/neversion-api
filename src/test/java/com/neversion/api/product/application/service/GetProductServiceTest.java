package com.neversion.api.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.model.enums.CategoryType;
import com.neversion.api.product.domain.port.out.ProductRepositoryPort;

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
            UUID id = UUID.randomUUID();
            Product expected = Product.builder()
                    .id(id)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.STREAMING)
                    .build();

            when(productRepositoryPort.findById(id)).thenReturn(Optional.of(expected));

            Product result = getProductService.getById(id);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(id);
            assertThat(result.getName()).isEqualTo("Netflix");
            assertThat(result.getCategory()).isEqualTo(CategoryType.STREAMING);
            verify(productRepositoryPort).findById(id);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when Product does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            UUID id = UUID.randomUUID();
            when(productRepositoryPort.findById(id)).thenReturn(Optional.empty());

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
            String name = "Spotify";
            Product expected = Product.builder()
                    .id(UUID.randomUUID())
                    .name(name)
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.DIGITAL_SERVICE)
                    .build();

            when(productRepositoryPort.findByName(name)).thenReturn(Optional.of(expected));

            Product result = getProductService.getByName(name);

            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(name);
            assertThat(result.getCategory()).isEqualTo(CategoryType.DIGITAL_SERVICE);
            verify(productRepositoryPort).findByName(name);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when name does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            String name = "NonExistent";
            when(productRepositoryPort.findByName(name)).thenReturn(Optional.empty());

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
            Product netflix = Product.builder()
                    .id(UUID.randomUUID())
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.STREAMING)
                    .build();

            Product spotify = Product.builder()
                    .id(UUID.randomUUID())
                    .name("Spotify")
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.DIGITAL_SERVICE)
                    .build();

            when(productRepositoryPort.findAll()).thenReturn(List.of(netflix, spotify));

            List<Product> result = getProductService.getAll();

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
            when(productRepositoryPort.findAll()).thenReturn(Collections.emptyList());

            List<Product> result = getProductService.getAll();

            assertThat(result)
                    .isNotNull()
                    .isEmpty();
            verify(productRepositoryPort).findAll();
        }
    }

    @Nested
    @DisplayName("getByCategory")
    class GetByCategory {

        @Test
        @DisplayName("should return list of Products by category")
        void shouldReturnListOfProducts_byCategory() {
            CategoryType category = CategoryType.STREAMING;
            Product netflix = Product.builder()
                    .id(UUID.randomUUID())
                    .name("Netflix")
                    .category(category)
                    .build();

            Product disney = Product.builder()
                    .id(UUID.randomUUID())
                    .name("Disney+")
                    .category(category)
                    .build();

            when(productRepositoryPort.findByCategory(category)).thenReturn(List.of(netflix, disney));

            List<Product> result = getProductService.getByCategory(category);

            assertThat(result)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(Product::getName)
                    .containsExactly("Netflix", "Disney+");
            verify(productRepositoryPort).findByCategory(category);
        }

        @Test
        @DisplayName("should return empty list when no Products found for category")
        void shouldReturnEmptyList_whenNoProductsInCategory() {
            CategoryType category = CategoryType.STREAMING;
            when(productRepositoryPort.findByCategory(category)).thenReturn(Collections.emptyList());

            List<Product> result = getProductService.getByCategory(category);

            assertThat(result)
                    .isNotNull()
                    .isEmpty();
            verify(productRepositoryPort).findByCategory(category);
        }
    }
}
