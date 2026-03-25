package com.neversion.api.subscription.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
import com.neversion.api.exception.AccountOverbookingException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.shared.domain.model.enums.AccountStatus;
import com.neversion.api.shared.domain.model.enums.AccountType;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.subscription.domain.model.Subscription;
import com.neversion.api.subscription.domain.model.enums.SubStatus;
import com.neversion.api.subscription.domain.port.out.SubscriptionRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("SubscriptionService Unit Tests")
class SubscriptionServiceTest {

        @Mock
        private SubscriptionRepositoryPort subscriptionRepositoryPort;

        @Mock
        private AccountRepositoryPort accountRepositoryPort;

        @Mock
        private InventoryRepositoryPort inventoryRepositoryPort;

        private SubscriptionService subscriptionService;

        private static final UUID ACCOUNT_ID = UUID.randomUUID();
        private static final UUID USER_GUEST_ID = UUID.randomUUID();
        private static final UUID SUBSCRIPTION_ID = UUID.randomUUID();

        @BeforeEach
        void setUp() {
                subscriptionService = new SubscriptionService(subscriptionRepositoryPort, accountRepositoryPort, inventoryRepositoryPort);
        }

        @Nested
        @DisplayName("assign()")
        class Assign {

                @Test
                @DisplayName("should create subscription for familiar account (no overbooking check)")
                void shouldCreateSubscription_whenFamiliarAccount() {
                        // Given
                        Account familiarAccount = new Account(
                                        ACCOUNT_ID, "netflix@example.com", "pass123",
                                        1L, "Seller A", new BigDecimal("15.00"),
                                        AccountStatus.AVAILABLE,
                                        LocalDate.now().plusMonths(6));

                        Subscription input = Subscription.builder()
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .build();

                        Subscription persisted = Subscription.builder()
                                        .id(SUBSCRIPTION_ID)
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .status(SubStatus.ACTIVE)
                                        .build();

                        Inventory familiarInventory = Inventory.builder().id(1L).accountType(AccountType.FAMILIAR).build();

                        when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.of(familiarAccount));
                        when(inventoryRepositoryPort.findById(1L)).thenReturn(Optional.of(familiarInventory));
                        when(subscriptionRepositoryPort.save(any(Subscription.class))).thenReturn(persisted);

                        // When
                        Subscription result = subscriptionService.assign(input);

                        // Then
                        assertThat(result).isNotNull();
                        assertThat(result.getId()).isEqualTo(SUBSCRIPTION_ID);
                        assertThat(result.getStatus()).isEqualTo(SubStatus.ACTIVE);

                        // Familiar accounts skip overbooking check
                        verify(subscriptionRepositoryPort, never()).existsActiveByAccountId(any());
                        verify(subscriptionRepositoryPort, times(1)).save(any(Subscription.class));
                }

                @Test
                @DisplayName("should create subscription for individual account with no existing active subscriptions")
                void shouldCreateSubscription_whenIndividualAccountWithNoActiveSubs() {
                        // Given
                        Account individualAccount = new Account(
                                        ACCOUNT_ID, "hbo@example.com", "pass456",
                                        2L, "Seller B", new BigDecimal("10.00"),
                                        AccountStatus.AVAILABLE,
                                        LocalDate.now().plusMonths(3));

                        Subscription input = Subscription.builder()
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .build();

                        Subscription persisted = Subscription.builder()
                                        .id(SUBSCRIPTION_ID)
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .status(SubStatus.ACTIVE)
                                        .build();

                        Inventory individualInventory = Inventory.builder().id(2L).accountType(AccountType.INDIVIDUAL).build();

                        when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.of(individualAccount));
                        when(inventoryRepositoryPort.findById(2L)).thenReturn(Optional.of(individualInventory));
                        when(subscriptionRepositoryPort.existsActiveByAccountId(ACCOUNT_ID)).thenReturn(false);
                        when(subscriptionRepositoryPort.save(any(Subscription.class))).thenReturn(persisted);

                        // When
                        Subscription result = subscriptionService.assign(input);

                        // Then
                        assertThat(result).isNotNull();
                        assertThat(result.getStatus()).isEqualTo(SubStatus.ACTIVE);

                        verify(subscriptionRepositoryPort, times(1)).existsActiveByAccountId(ACCOUNT_ID);
                        verify(subscriptionRepositoryPort, times(1)).save(any(Subscription.class));
                }

                @Test
                @DisplayName("should throw AccountOverbookingException when individual account already has active subscription")
                void shouldThrowOverbookingException_whenIndividualAccountAlreadyActive() {
                        // Given
                        Account individualAccount = new Account(
                                        ACCOUNT_ID, "disney@example.com", "pass789",
                                        3L, "Seller C", new BigDecimal("12.00"),
                                        AccountStatus.ASSIGNED,
                                        LocalDate.now().plusMonths(3));

                        Subscription input = Subscription.builder()
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .build();

                        Inventory individualInventory = Inventory.builder().id(3L).accountType(AccountType.INDIVIDUAL).build();

                        when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.of(individualAccount));
                        when(inventoryRepositoryPort.findById(3L)).thenReturn(Optional.of(individualInventory));
                        when(subscriptionRepositoryPort.existsActiveByAccountId(ACCOUNT_ID)).thenReturn(true);

                        // When & Then
                        assertThatThrownBy(() -> subscriptionService.assign(input))
                                        .isInstanceOf(AccountOverbookingException.class)
                                        .hasMessageContaining("already has an active subscription");

                        verify(subscriptionRepositoryPort, never()).save(any());
                }

                @Test
                @DisplayName("should throw ResourceNotFoundException when account does not exist")
                void shouldThrowNotFoundException_whenAccountNotFound() {
                        // Given
                        Subscription input = Subscription.builder()
                                        .accountId(ACCOUNT_ID)
                                        .userGuestId(USER_GUEST_ID)
                                        .purchaseDate(LocalDate.now())
                                        .renewalDate(LocalDate.now().plusDays(30))
                                        .build();

                        when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

                        // When & Then
                        assertThatThrownBy(() -> subscriptionService.assign(input))
                                        .isInstanceOf(ResourceNotFoundException.class)
                                        .hasMessageContaining("Account not found");

                        verify(subscriptionRepositoryPort, never()).existsActiveByAccountId(any());
                        verify(subscriptionRepositoryPort, never()).save(any());
                }
        }
}
