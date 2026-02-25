package com.neversion.panel.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateProductService Unit Tests")
class CreateProductServiceTest {

        @Mock
        private ProductRepositoryPort productRepositoryPort;

        private CreateProductService createProductService;

        @BeforeEach
        void setUp() {
                createProductService = new CreateProductService(productRepositoryPort);
        }

        @Test
        @DisplayName("create - should return the saved Product with generated id")
        void create_shouldReturnSavedProduct() {
                // Given
                Product input = Product.builder()
                                .name("Netflix")
                                .description("Streaming platform")
                                .imageUrl("https://img.example.com/netflix.png")
                                .category(CategoryType.PLATAFORMA)
                                .build();

                Product persisted = Product.builder()
                                .id(1)
                                .name("Netflix")
                                .description("Streaming platform")
                                .imageUrl("https://img.example.com/netflix.png")
                                .category(CategoryType.PLATAFORMA)
                                .build();

                when(productRepositoryPort.save(input)).thenReturn(persisted);

                // When
                Product result = createProductService.create(input);

                // Then
                assertThat(result).isNotNull();
                assertThat(result.getId()).isEqualTo(1);
                assertThat(result.getName()).isEqualTo("Netflix");
                assertThat(result.getDescription()).isEqualTo("Streaming platform");
                assertThat(result.getCategory()).isEqualTo(CategoryType.PLATAFORMA);
        }

        @Test
        @DisplayName("create - should delegate to repository port exactly once")
        void create_shouldDelegateToRepositoryPort() {
                // Given
                Product input = Product.builder()
                                .name("Spotify")
                                .description("Music streaming")
                                .imageUrl("https://img.example.com/spotify.png")
                                .category(CategoryType.SUSCRIPCION)
                                .build();

                when(productRepositoryPort.save(input)).thenReturn(input);

                // When
                createProductService.create(input);

                // Then
                verify(productRepositoryPort, times(1)).save(input);
                verifyNoMoreInteractions(productRepositoryPort);
        }
}
