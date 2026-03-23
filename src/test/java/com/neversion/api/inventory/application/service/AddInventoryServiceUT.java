package com.neversion.api.inventory.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.inventory.domain.service.InventoryPricingService;
import com.neversion.api.product.application.port.in.GetProductUseCase;
import com.neversion.api.product.domain.model.Product;
import com.neversion.api.shared.domain.model.enums.AccountType;

@ExtendWith(MockitoExtension.class)
@DisplayName("AddInventoryService unit tests")
class AddInventoryServiceUT {

    @Mock
    private InventoryRepositoryPort inventoryRepositoryPort;

    @Mock
    private GetProductUseCase getProductUseCase;

    @Mock
    private InventoryPricingService inventoryPricingService;

    private AddInventoryService addInventoryService;

    private static final UUID PRODUCT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        addInventoryService = new AddInventoryService(
                inventoryRepositoryPort, getProductUseCase, inventoryPricingService);
    }

    @Nested
    @DisplayName("add")
    class Add {

        @Test
        @DisplayName("add - should validate product exists, apply pricing, and save")
        void add_shouldValidateProductExistsApplyPricingAndSave() {
            // Given
            Product product = Product.builder().id(PRODUCT_ID).name("Netflix").build();
            Inventory inventory = Inventory.builder()
                    .price(BigDecimal.valueOf(9.99))
                    .durationDays(30)
                    .accountType(AccountType.INDIVIDUAL)
                    .stock(10)
                    .build();
            Inventory saved = Inventory.builder()
                    .id(1L)
                    .productId(PRODUCT_ID)
                    .price(BigDecimal.valueOf(9.99))
                    .durationDays(30)
                    .accountType(AccountType.INDIVIDUAL)
                    .stock(10)
                    .build();

            when(getProductUseCase.getById(PRODUCT_ID)).thenReturn(product);
            when(inventoryRepositoryPort.save(any(Inventory.class))).thenReturn(saved);

            // When
            Inventory result = addInventoryService.add(PRODUCT_ID, inventory);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
            verify(getProductUseCase).getById(PRODUCT_ID);
            verify(inventoryPricingService).applyDurationDiscount(inventory);
            verify(inventoryRepositoryPort).save(inventory);
        }

        @Test
        @DisplayName("add - should propagate exception when product not found")
        void add_shouldPropagateException_whenProductNotFound() {
            // Given
            Inventory inventory = Inventory.builder().price(BigDecimal.TEN).durationDays(30).build();
            when(getProductUseCase.getById(PRODUCT_ID))
                    .thenThrow(new ResourceNotFoundException("Product not found"));

            // When / Then
            assertThatThrownBy(() -> addInventoryService.add(PRODUCT_ID, inventory))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }
}
