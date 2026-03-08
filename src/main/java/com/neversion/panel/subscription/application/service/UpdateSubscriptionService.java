package com.neversion.panel.subscription.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.exception.BusinessRuleException;
import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.subscription.application.port.in.UpdateSubscriptionUseCase;
import com.neversion.panel.subscription.domain.model.Subscription;
import com.neversion.panel.subscription.domain.model.enums.SubStatus;
import com.neversion.panel.subscription.domain.port.out.SubscriptionRepositoryPort;

/**
 * CU-A06: Manages subscription lifecycle transitions.
 */
@Service
public class UpdateSubscriptionService implements UpdateSubscriptionUseCase {

    private final SubscriptionRepositoryPort subscriptionRepositoryPort;

    public UpdateSubscriptionService(SubscriptionRepositoryPort subscriptionRepositoryPort) {
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
    }

    @Override
    @Transactional
    public Subscription suspend(UUID id) {
        Subscription subscription = findOrThrow(id);

        if (subscription.getStatus() != SubStatus.ACTIVE) {
            throw new BusinessRuleException(
                    "Only ACTIVE subscriptions can be suspended. Current: " + subscription.getStatus());
        }

        subscription.setStatus(SubStatus.SUSPENDED);
        return subscriptionRepositoryPort.save(subscription);
    }

    @Override
    @Transactional
    public Subscription terminate(UUID id) {
        Subscription subscription = findOrThrow(id);

        if (subscription.getStatus() == SubStatus.CANCELLED) {
            throw new BusinessRuleException("Subscription is already cancelled.");
        }

        subscription.setStatus(SubStatus.CANCELLED);
        return subscriptionRepositoryPort.save(subscription);
    }

    private Subscription findOrThrow(UUID id) {
        return subscriptionRepositoryPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Subscription not found with id: " + id));
    }
}
