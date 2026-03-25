package com.neversion.api.account.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.domain.port.out.AccountRepositoryPort;
import com.neversion.api.accountslot.application.port.in.AccountSlotUseCase;
import com.neversion.api.exception.BusinessRuleException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.shared.domain.model.enums.AccountStatus;
import com.neversion.api.shared.domain.model.enums.AccountType;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateAccountService unit tests")
class CreateAccountServiceUT {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    @Mock
    private InventoryRepositoryPort inventoryRepositoryPort;

    @Mock
    private AccountSlotUseCase accountSlotUseCase;

    private CreateAccountService createAccountService;

    @BeforeEach
    void setUp() {
        createAccountService = new CreateAccountService(
                accountRepositoryPort, inventoryRepositoryPort, accountSlotUseCase);
    }

    private Account buildAccount(LocalDate expiration) {
        return Account.builder()
                .email("test@gmail.com")
                .pass("pass123")
                .inventoryId(1L)
                .seller("seller1")
                .priceSeller(BigDecimal.TEN)
                .status(AccountStatus.AVAILABLE)
                .expirationDate(expiration)
                .build();
    }

    @Nested
    @DisplayName("create")
    class Create {

        @Test
        @DisplayName("create - should save individual account and generate 1 slot")
        void create_shouldSaveIndividualAccountAndGenerate1Slot() {
            // Given
            Account account = buildAccount(LocalDate.now().plusDays(30));
            UUID savedId = UUID.randomUUID();
            Account saved = buildAccount(LocalDate.now().plusDays(30));
            saved.setId(savedId);

            Inventory inventory = Inventory.builder().id(1L).accountType(AccountType.INDIVIDUAL).build();

            when(accountRepositoryPort.save(account)).thenReturn(saved);
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            Account result = createAccountService.create(account);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(savedId);
            verify(accountSlotUseCase).generateSlotsForAccount(savedId, 1);
        }

        @Test
        @DisplayName("create - should save familiar account and generate slots from inventory maxProfiles")
        void create_shouldSaveFamiliarAccountAndGenerateSlotsFromMaxProfiles() {
            // Given
            Account account = buildAccount(LocalDate.now().plusDays(30));
            UUID savedId = UUID.randomUUID();
            Account saved = buildAccount(LocalDate.now().plusDays(30));
            saved.setId(savedId);

            Inventory inventory = Inventory.builder().id(1L).accountType(AccountType.FAMILIAR).maxProfiles(5).build();

            when(accountRepositoryPort.save(account)).thenReturn(saved);
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            Account result = createAccountService.create(account);

            // Then
            assertThat(result).isNotNull();
            verify(accountSlotUseCase).generateSlotsForAccount(savedId, 5);
        }

        @Test
        @DisplayName("create - should throw BusinessRuleException when expiration is in the past")
        void create_shouldThrowBusinessRuleException_whenExpirationInPast() {
            // Given
            Account account = buildAccount(LocalDate.now().minusDays(1));

            // When / Then
            assertThatThrownBy(() -> createAccountService.create(account))
                    .isInstanceOf(BusinessRuleException.class)
                    .hasMessageContaining("cannot be in the past");
        }

        @Test
        @DisplayName("create - should throw ResourceNotFoundException when familiar inventory not found")
        void create_shouldThrowResourceNotFound_whenFamiliarInventoryNotFound() {
            // Given
            Account account = buildAccount(LocalDate.now().plusDays(30));
            UUID savedId = UUID.randomUUID();
            Account saved = buildAccount(LocalDate.now().plusDays(30));
            saved.setId(savedId);

            when(accountRepositoryPort.save(account)).thenReturn(saved);
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> createAccountService.create(account))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining("Inventory not found");
        }

        @Test
        @DisplayName("create - should default to 1 slot when familiar inventory has null maxProfiles")
        void create_shouldDefaultTo1Slot_whenMaxProfilesIsNull() {
            // Given
            Account account = buildAccount(LocalDate.now().plusDays(30));
            UUID savedId = UUID.randomUUID();
            Account saved = buildAccount(LocalDate.now().plusDays(30));
            saved.setId(savedId);

            Inventory inventory = Inventory.builder().id(1L).accountType(AccountType.FAMILIAR).maxProfiles(null).build();

            when(accountRepositoryPort.save(account)).thenReturn(saved);
            when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(inventory));

            // When
            createAccountService.create(account);

            // Then
            verify(accountSlotUseCase).generateSlotsForAccount(savedId, 1);
        }
    }
}
