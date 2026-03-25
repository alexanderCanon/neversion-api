package com.neversion.api.subscription.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.account.domain.model.Account;
import com.neversion.api.account.domain.port.out.AccountRepositoryPort;
import com.neversion.api.exception.AccountOverbookingException;
import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.shared.domain.model.enums.AccountType;
import com.neversion.api.subscription.application.port.in.AssignAccountUseCase;
import com.neversion.api.subscription.domain.model.Subscription;
import com.neversion.api.inventory.domain.model.Inventory;
import com.neversion.api.inventory.domain.port.out.InventoryRepositoryPort;
import com.neversion.api.subscription.domain.model.enums.SubStatus;
import com.neversion.api.subscription.domain.port.out.SubscriptionRepositoryPort;

@Service
public class SubscriptionService implements AssignAccountUseCase {

    private final SubscriptionRepositoryPort subscriptionRepositoryPort;
    private final AccountRepositoryPort accountRepositoryPort;
    private final InventoryRepositoryPort inventoryRepositoryPort;

    public SubscriptionService(SubscriptionRepositoryPort subscriptionRepositoryPort,
            AccountRepositoryPort accountRepositoryPort,
            InventoryRepositoryPort inventoryRepositoryPort) {
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
        this.accountRepositoryPort = accountRepositoryPort;
        this.inventoryRepositoryPort = inventoryRepositoryPort;
    }

    /**
     * Assigns an account to a subscription (manual fulfillment by Admin).
     *
     * Anti-Overbooking Logic (BR-06):
     * Before creating the subscription, the service checks if the account
     * is of type 'individual'. If so, it verifies there are no currently
     * 'active' subscriptions for that account. This prevents the same
     * individual credential from being sold to two customers simultaneously.
     *
     * Familiar accounts are shared among profiles, so they skip this check.
     */
    @Override
    @Transactional
    public Subscription assign(Subscription subscription) {
        // 1. Load the account to verify it exists and check its type
        Account account = accountRepositoryPort.findById(subscription.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Account not found with id: " + subscription.getAccountId()));

        Inventory inventory = inventoryRepositoryPort.findById(account.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inventory not found: " + account.getInventoryId()));

        // 2. Anti-overbooking: individual accounts can only have ONE active
        // subscription
        if (inventory.getAccountType() == AccountType.INDIVIDUAL) {
            boolean hasActiveSubscription = subscriptionRepositoryPort
                    .existsActiveByAccountId(subscription.getAccountId());

            if (hasActiveSubscription) {
                throw new AccountOverbookingException(
                        "Individual account " + subscription.getAccountId()
                                + " already has an active subscription. "
                                + "Cannot assign the same individual account to multiple customers.");
            }
        }

        // 3. Set default status and persist
        subscription.setStatus(SubStatus.ACTIVE);
        return subscriptionRepositoryPort.save(subscription);
    }
}
