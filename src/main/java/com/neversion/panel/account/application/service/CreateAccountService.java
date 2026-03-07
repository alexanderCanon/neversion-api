package com.neversion.panel.account.application.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.account.application.port.in.CreateAccountUseCase;
import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.account.domain.port.out.AccountRepositoryPort;
import com.neversion.panel.accountslot.application.port.in.AccountSlotUseCase;
import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.inventory.domain.model.Inventory;
import com.neversion.panel.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.panel.shared.domain.model.enums.AccountType;

@Service
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepositoryPort accountRepositoryPort;
    private final InventoryRepositoryPort inventoryRepositoryPort;
    private final AccountSlotUseCase accountSlotUseCase;

    public CreateAccountService(AccountRepositoryPort accountRepositoryPort,
            InventoryRepositoryPort inventoryRepositoryPort,
            AccountSlotUseCase accountSlotUseCase) {
        this.accountRepositoryPort = accountRepositoryPort;
        this.inventoryRepositoryPort = inventoryRepositoryPort;
        this.accountSlotUseCase = accountSlotUseCase;
    }

    @Override
    @Transactional
    public Account create(Account account) {
        if (account.getExpirationDate().isBefore(LocalDate.now().plusDays(15))) {
            throw new BusinessRuleException("Expiration date must be at least 15 days from now");
        }

        Account saved = accountRepositoryPort.save(account);

        // Auto-generate account slots based on account type and inventory max_profiles
        int slotCount = resolveSlotCount(account);
        accountSlotUseCase.generateSlotsForAccount(saved.getId(), slotCount);

        return saved;
    }

    /**
     * Individual accounts get 1 slot.
     * Familiar accounts get N slots determined by inventory.max_profiles.
     */
    private int resolveSlotCount(Account account) {
        if (account.getAccountType() == AccountType.INDIVIDUAL) {
            return 1;
        }

        Inventory inventory = inventoryRepositoryPort.findById(account.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found: " + account.getInventoryId()));

        return inventory.getMaxProfiles() != null ? inventory.getMaxProfiles() : 1;
    }
}
