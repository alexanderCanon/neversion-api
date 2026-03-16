package com.neversion.panel.inventory.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.shared.domain.model.enums.AccountType;

/**
 * Unit tests for inventory mutation services:
 * DeleteInventoryService, UpdateInventoryPriceService,
 * IncreaseStockService, DecreaseStockService, UpdateInventoryStockService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Inventory mutation services unit tests")
class InventoryMutationServicesUT {

    @Mock
    private InventoryRepositoryPort inventoryRepositoryPort;

    private DeleteInventoryService deleteInventoryService;
    private UpdateInventoryPriceService updateInventoryPriceService;
    private IncreaseStockService increaseStockService;
    private DecreaseStockService decreaseStockService;
    private UpdateInventoryStockService updateInventoryStockService;

    @BeforeEach
    void setUp() {
        deleteInventoryService = new DeleteInventoryService(inventoryRepositoryPort);
        updateInventoryPriceService = new UpdateInventoryPriceService(inventoryRepositoryPort);
        increaseStockService = new IncreaseStockService(inventoryRepositoryPort);
        decreaseStockService = new DecreaseStockService(inventoryRepositoryPort);
        updateInventoryStockService = new UpdateInventoryStockService(inventoryRepositoryPort);
    }

    private Inventory buildInventory() {
        return Inventory.builder()
                .id(1L)
                .productId(UUID.randomUUID())
                .price(BigDecimal.valueOf(9.99))
                .durationDays(30)
                .accountType(AccountType.INDIVIDUAL)
                .stock(10)
                .build();
    }

    // ── DeleteInventoryService ────────────────────────────────────────

    @Nested
    @DisplayName("DeleteInventoryService")
    class Delete {

        @Test
        @DisplayName("delete - should delete when inventory exists")
        void delete_shouldDelete_whenExists() {
            // Given
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(buildInventory()));

            // When
            deleteInventoryService.delete(1L);

            // Then
            verify(inventoryRepositoryPort).deleteById(1L);
        }

        @Test
        @DisplayName("delete - should throw ResourceNotFoundException when not found")
        void delete_shouldThrowResourceNotFound_whenNotFound() {
            when(inventoryRepositoryPort.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> deleteInventoryService.delete(99L))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    // ── UpdateInventoryPriceService ───────────────────────────────────

    @Nested
    @DisplayName("UpdateInventoryPriceService")
    class UpdatePrice {

        @Test
        @DisplayName("updatePrice - should update price when valid")
        void updatePrice_shouldUpdatePrice_whenValid() {
            // Given
            Inventory inventory = buildInventory();
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            updateInventoryPriceService.updatePrice(1L, BigDecimal.valueOf(14.99));

            // Then
            assertThat(inventory.getPrice()).isEqualByComparingTo(BigDecimal.valueOf(14.99));
            verify(inventoryRepositoryPort).save(inventory);
        }

        @Test
        @DisplayName("updatePrice - should throw BusinessRuleException when price is negative")
        void updatePrice_shouldThrowBusinessRule_whenNegativePrice() {
            assertThatThrownBy(() -> updateInventoryPriceService.updatePrice(1L, BigDecimal.valueOf(-1)))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("negative");
        }

        @Test
        @DisplayName("updatePrice - should throw ResourceNotFoundException when not found")
        void updatePrice_shouldThrowResourceNotFound_whenNotFound() {
            when(inventoryRepositoryPort.findById(99L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> updateInventoryPriceService.updatePrice(99L, BigDecimal.TEN))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }

    // ── IncreaseStockService ──────────────────────────────────────────

    @Nested
    @DisplayName("IncreaseStockService")
    class Increase {

        @Test
        @DisplayName("increase - should add quantity to current stock")
        void increase_shouldAddQuantityToCurrentStock() {
            // Given
            Inventory inventory = buildInventory(); // stock = 10
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            increaseStockService.increase(1L, 5);

            // Then
            assertThat(inventory.getStock()).isEqualTo(15);
            verify(inventoryRepositoryPort).save(inventory);
        }

        @Test
        @DisplayName("increase - should throw BusinessRuleException when quantity is negative")
        void increase_shouldThrowBusinessRule_whenNegativeQuantity() {
            assertThatThrownBy(() -> increaseStockService.increase(1L, -1))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("positive");
        }
    }

    // ── DecreaseStockService ──────────────────────────────────────────

    @Nested
    @DisplayName("DecreaseStockService")
    class Decrease {

        @Test
        @DisplayName("decrease - should subtract quantity from current stock")
        void decrease_shouldSubtractQuantityFromCurrentStock() {
            // Given
            Inventory inventory = buildInventory(); // stock = 10
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            decreaseStockService.decrease(1L, 3);

            // Then
            assertThat(inventory.getStock()).isEqualTo(7);
            verify(inventoryRepositoryPort).save(inventory);
        }

        @Test
        @DisplayName("decrease - should throw BusinessRuleException when insufficient stock")
        void decrease_shouldThrowBusinessRule_whenInsufficientStock() {
            // Given
            Inventory inventory = buildInventory(); // stock = 10
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When / Then
            assertThatThrownBy(() -> decreaseStockService.decrease(1L, 15))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("Insufficient stock");
        }
    }

    // ── UpdateInventoryStockService ───────────────────────────────────

    @Nested
    @DisplayName("UpdateInventoryStockService")
    class UpdateStock {

        @Test
        @DisplayName("updateStock - should set stock to new value")
        void updateStock_shouldSetStockToNewValue() {
            // Given
            Inventory inventory = buildInventory(); // stock = 10
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            updateInventoryStockService.updateStock(1L, 25);

            // Then
            assertThat(inventory.getStock()).isEqualTo(25);
            verify(inventoryRepositoryPort).save(inventory);
        }

        @Test
        @DisplayName("updateStock - should throw BusinessRuleException when stock is negative")
        void updateStock_shouldThrowBusinessRule_whenNegativeStock() {
            assertThatThrownBy(() -> updateInventoryStockService.updateStock(1L, -1))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("negative");
        }
    }
}
