package com.neversion.panel.product.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.BusinessRuleException;
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

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("should return the saved Product with generated id")
        void shouldReturnSavedProduct() {
            UUID generatedId = UUID.randomUUID();

            Product input = Product.builder()
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.STREAMING)
                    .build();

            Product persisted = Product.builder()
                    .id(generatedId)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.STREAMING)
                    .build();

            when(productRepositoryPort.existsByName("Netflix")).thenReturn(false);
            when(productRepositoryPort.save(input)).thenReturn(persisted);

            Product result = createProductService.create(input);

            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(generatedId);
            assertThat(result.getName()).isEqualTo("Netflix");
            assertThat(result.getDescription()).isEqualTo("Streaming platform");
            assertThat(result.getCategory()).isEqualTo(CategoryType.STREAMING);
        }

        @Test
        @DisplayName("should delegate to repository port exactly once")
        void shouldDelegateToRepositoryPort() {
            Product input = Product.builder()
                    .name("Spotify")
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.SUSCRIP4U)
                    .build();

            when(productRepositoryPort.existsByName("Spotify")).thenReturn(false);
            when(productRepositoryPort.save(input)).thenReturn(input);

            createProductService.create(input);

            verify(productRepositoryPort, times(1)).save(input);
            verify(productRepositoryPort, times(1)).existsByName("Spotify");
            verifyNoMoreInteractions(productRepositoryPort);
        }

        @Test
        @DisplayName("should throw exception when name is less than 3 characters")
        void shouldThrowException_whenNameTooShort() {
            Product input = Product.builder()
                    .name("AB")
                    .category(CategoryType.STREAMING)
                    .build();

            assertThatThrownBy(() -> createProductService.create(input))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("at least 3 characters");
        }

        @Test
        @DisplayName("should throw exception when name already exists")
        void shouldThrowException_whenDuplicateName() {
            Product input = Product.builder()
                    .name("Netflix")
                    .category(CategoryType.STREAMING)
                    .build();

            when(productRepositoryPort.existsByName("Netflix")).thenReturn(true);

            assertThatThrownBy(() -> createProductService.create(input))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("already exists");
        }
    }
}
