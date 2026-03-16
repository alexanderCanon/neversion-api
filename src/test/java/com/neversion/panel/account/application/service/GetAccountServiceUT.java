package com.neversion.panel.account.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
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

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.shared.domain.model.enums.AccountStatus;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAccountService unit tests")
class GetAccountServiceUT {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private GetAccountService getAccountService;

    private static final UUID ACCOUNT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        getAccountService = new GetAccountService(accountRepositoryPort);
    }

    private Account buildAccount() {
        return Account.builder()
                .id(ACCOUNT_ID)
                .email("test@gmail.com")
                .pass("pass123")
                .inventoryId(1L)
                .seller("seller1")
                .priceSeller(BigDecimal.TEN)
                .accountType(AccountType.INDIVIDUAL)
                .status(AccountStatus.AVAILABLE)
                .expirationDate(LocalDate.now().plusDays(30))
                .build();
    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        @DisplayName("getById - should return account when found")
        void getById_shouldReturnAccount_whenFound() {
            // Given
            Account account = buildAccount();
            when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

            // When
            Account result = getAccountService.getById(ACCOUNT_ID);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(ACCOUNT_ID);
        }

        @Test
        @DisplayName("getById - should throw ResourceNotFoundException when not found")
        void getById_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getAccountService.getById(ACCOUNT_ID))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(ACCOUNT_ID.toString());
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("getAll - should return all accounts")
        void getAll_shouldReturnAllAccounts() {
            // Given
            List<Account> accounts = List.of(buildAccount());
            when(accountRepositoryPort.findAll()).thenReturn(accounts);

            // When
            List<Account> result = getAccountService.getAll();

            // Then
            assertThat(result).hasSize(1);
        }
    }

    @Nested
    @DisplayName("getBySeller")
    class GetBySeller {

        @Test
        @DisplayName("getBySeller - should delegate to repository")
        void getBySeller_shouldDelegateToRepository() {
            // Given
            List<Account> accounts = List.of(buildAccount());
            when(accountRepositoryPort.findBySeller("seller1")).thenReturn(accounts);

            // When
            List<Account> result = getAccountService.getBySeller("seller1");

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
            List<Account> accounts = List.of(buildAccount());
            when(accountRepositoryPort.findByAccountType(AccountType.INDIVIDUAL)).thenReturn(accounts);

            // When
            List<Account> result = getAccountService.getByAccountType(AccountType.INDIVIDUAL);

            // Then
            assertThat(result).hasSize(1);
        }
    }
}
