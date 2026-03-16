package com.neversion.panel.product.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.product.domain.model.Product;
import com.neversion.panel.product.domain.model.enums.CategoryType;
import com.neversion.panel.product.domain.port.out.ProductRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateProductService unit tests")
class UpdateProductServiceUT {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    private UpdateProductService updateProductService;

    private static final UUID PRODUCT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        updateProductService = new UpdateProductService(productRepositoryPort);
    }

    private Product buildExisting() {
        return Product.builder()
                .id(PRODUCT_ID)
                .name("Netflix")
                .description("Streaming service")
                .imageUrl("https://img.com/netflix.png")
                .category(CategoryType.STREAMING)
                .build();
    }

    @Nested
    @DisplayName("update")
    class Update {

        @Test
        @DisplayName("update - should update product fields and save")
        void update_shouldUpdateFieldsAndSave() {
            // Given
            Product existing = buildExisting();
            Product updateData = Product.builder()
                    .name("Netflix Premium")
                    .description("Updated description")
                    .imageUrl("https://img.com/netflix2.png")
                    .category(CategoryType.SOFTWARE)
                    .build();

            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(existing));
            when(productRepositoryPort.findByName("Netflix Premium")).thenReturn(Optional.empty());
            when(productRepositoryPort.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

            // When
            Product result = updateProductService.update(PRODUCT_ID, updateData);

            // Then
            assertThat(result.getName()).isEqualTo("Netflix Premium");
            assertThat(result.getDescription()).isEqualTo("Updated description");
            assertThat(result.getCategory()).isEqualTo(CategoryType.SOFTWARE);
        }

        @Test
        @DisplayName("update - should throw ResourceNotFoundException when product not found")
        void update_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            Product updateData = Product.builder().name("New Name").build();
            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> updateProductService.update(PRODUCT_ID, updateData))
                    .isInstanceOf(ResourceNotFoundException.class);
        }

        @Test
        @DisplayName("update - should throw BusinessRuleException when name is too short")
        void update_shouldThrowBusinessRule_whenNameTooShort() {
            // Given
            Product existing = buildExisting();
            Product updateData = Product.builder().name("AB").build();

            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(existing));

            // When / Then
            assertThatThrownBy(() -> updateProductService.update(PRODUCT_ID, updateData))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("at least");
        }

        @Test
        @DisplayName("update - should throw BusinessRuleException when duplicate name belongs to another product")
        void update_shouldThrowBusinessRule_whenDuplicateNameOnDifferentProduct() {
            // Given
            Product existing = buildExisting();
            Product updateData = Product.builder()
                    .name("Disney+")
                    .description("desc")
                    .category(CategoryType.STREAMING)
                    .build();

            UUID otherId = UUID.randomUUID();
            Product otherProduct = Product.builder().id(otherId).name("Disney+").build();

            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(existing));
            when(productRepositoryPort.findByName("Disney+")).thenReturn(Optional.of(otherProduct));

            // When / Then
            assertThatThrownBy(() -> updateProductService.update(PRODUCT_ID, updateData))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("already exists");
        }

        @Test
        @DisplayName("update - should allow keeping same name on same product")
        void update_shouldAllowSameName_onSameProduct() {
            // Given
            Product existing = buildExisting();
            Product updateData = Product.builder()
                    .name("Netflix")
                    .description("New desc")
                    .category(CategoryType.STREAMING)
                    .build();

            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(existing));
            when(productRepositoryPort.findByName("Netflix")).thenReturn(Optional.of(existing));
            when(productRepositoryPort.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));

            // When
            Product result = updateProductService.update(PRODUCT_ID, updateData);

            // Then
            assertThat(result.getName()).isEqualTo("Netflix");
            assertThat(result.getDescription()).isEqualTo("New desc");
        }
    }
}
