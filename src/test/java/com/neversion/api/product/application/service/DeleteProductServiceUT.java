package com.neversion.api.product.application.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
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

import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.product.domain.port.out.ProductRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeleteProductService unit tests")
class DeleteProductServiceUT {

    @Mock
    private ProductRepositoryPort productRepositoryPort;

    @Mock
    private InventoryRepositoryPort inventoryRepositoryPort;

    private DeleteProductService deleteProductService;

    private static final UUID PRODUCT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        deleteProductService = new DeleteProductService(productRepositoryPort, inventoryRepositoryPort);
    }

    @Nested
    @DisplayName("delete")
    class Delete {

        @Test
        @DisplayName("delete - should delete product when exists and has no inventories")
        void delete_shouldDeleteProduct_whenExistsAndNoInventories() {
            // Given
            Product product = Product.builder().id(PRODUCT_ID).name("Netflix").build();
            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
            when(inventoryRepositoryPort.existsByProductId(PRODUCT_ID)).thenReturn(false);

            // When
            deleteProductService.delete(PRODUCT_ID);

            // Then
            verify(productRepositoryPort).deleteById(PRODUCT_ID);
        }

        @Test
        @DisplayName("delete - should throw ResourceNotFoundException when product not found")
        void delete_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> deleteProductService.delete(PRODUCT_ID))
                    .isInstanceOf(ResourceNotFoundException.class);
        }

        @Test
        @DisplayName("delete - should throw BusinessRuleException when product has active inventories")
        void delete_shouldThrowBusinessRule_whenHasActiveInventories() {
            // Given
            Product product = Product.builder().id(PRODUCT_ID).name("Netflix").build();
            when(productRepositoryPort.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
            when(inventoryRepositoryPort.existsByProductId(PRODUCT_ID)).thenReturn(true);

            // When / Then
            assertThatThrownBy(() -> deleteProductService.delete(PRODUCT_ID))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("active inventories");
        }
    }
}
