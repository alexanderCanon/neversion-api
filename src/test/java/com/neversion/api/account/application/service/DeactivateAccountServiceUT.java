package com.neversion.api.account.application.service;

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
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.shared.domain.model.enums.AccountStatus;

@ExtendWith(MockitoExtension.class)
@DisplayName("DeactivateAccountService unit tests")
class DeactivateAccountServiceUT {

    @Mock
    private AccountRepositoryPort accountRepositoryPort;

    private DeactivateAccountService deactivateAccountService;

    private static final UUID ACCOUNT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        deactivateAccountService = new DeactivateAccountService(accountRepositoryPort);
    }

    @Nested
    @DisplayName("deactivate")
    class Deactivate {

        @Test
        @DisplayName("deactivate - should deactivate when account exists")
        void deactivate_shouldDeactivate_whenAccountExists() {
            // Given
            Account account = Account.builder()
                    .id(ACCOUNT_ID)
                    .email("test@gmail.com")
                    .pass("pass")
                    .inventoryId(1L)
                    .seller("seller")
                    .priceSeller(BigDecimal.TEN)
                    .status(AccountStatus.AVAILABLE)
                    .expirationDate(LocalDate.now().plusDays(30))
                    .build();
            when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.of(account));

            // When
            deactivateAccountService.deactivate(ACCOUNT_ID);

            // Then
            verify(accountRepositoryPort).deactivate(ACCOUNT_ID);
        }

        @Test
        @DisplayName("deactivate - should throw ResourceNotFoundException when not found")
        void deactivate_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(accountRepositoryPort.findById(ACCOUNT_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> deactivateAccountService.deactivate(ACCOUNT_ID))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(ACCOUNT_ID.toString());
        }
    }
}
