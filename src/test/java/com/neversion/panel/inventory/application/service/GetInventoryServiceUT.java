package com.neversion.panel.inventory.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetInventoryService unit tests")
class GetInventoryServiceUT {

    @Mock
    private InventoryRepositoryPort inventoryRepositoryPort;

    private GetInventoryService getInventoryService;

    @BeforeEach
    void setUp() {
        getInventoryService = new GetInventoryService(inventoryRepositoryPort);
    }

    private Inventory buildInventory(Long id) {
        return Inventory.builder()
                .id(id)
                .productId(UUID.randomUUID())
                .price(BigDecimal.valueOf(9.99))
                .durationDays(30)
                .accountType(AccountType.INDIVIDUAL)
                .stock(10)
                .build();
    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        @DisplayName("getById - should return inventory when found")
        void getById_shouldReturnInventory_whenFound() {
            // Given
            Inventory inventory = buildInventory(1L);
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            Inventory result = getInventoryService.getById(1L);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(1L);
        }

        @Test
        @DisplayName("getById - should throw ResourceNotFoundException when not found")
        void getById_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(inventoryRepositoryPort.findById(99L)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getInventoryService.getById(99L))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("99");
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("getAll - should return all inventories")
        void getAll_shouldReturnAllInventories() {
            // Given
            List<Inventory> inventories = List.of(buildInventory(1L), buildInventory(2L));
            when(inventoryRepositoryPort.findAll()).thenReturn(inventories);

            // When
            List<Inventory> result = getInventoryService.getAll();

            // Then
            assertThat(result).hasSize(2);
        }
    }

    @Nested
    @DisplayName("getByProductId")
    class GetByProductId {

        @Test
        @DisplayName("getByProductId - should delegate to repository")
        void getByProductId_shouldDelegateToRepository() {
            // Given
            UUID productId = UUID.randomUUID();
            List<Inventory> inventories = List.of(buildInventory(1L));
            when(inventoryRepositoryPort.findByProductId(productId)).thenReturn(inventories);

            // When
            List<Inventory> result = getInventoryService.getByProductId(productId);

            // Then
            assertThat(result).hasSize(1);
        }
    }

    @Nested
    @DisplayName("getByAccountType")
    class GetByAccountType {

        @Test
        @DisplayName("getByAccountType - should delegate to repository")
        void getByAccountType_shouldDelegateToRepository() {
            // Given
            List<Inventory> inventories = List.of(buildInventory(1L));
            when(inventoryRepositoryPort.findByAccountType(AccountType.FAMILIAR)).thenReturn(inventories);

            // When
            List<Inventory> result = getInventoryService.getByAccountType(AccountType.FAMILIAR);

            // Then
            assertThat(result).hasSize(1);
        }
    }
}
