package com.neversion.panel.subscription.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.panel.subscription.domain.model.Subscription;
import com.neversion.panel.subscription.domain.model.enums.SubStatus;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

public interface SubscriptionRepositoryPort {

    Subscription save(Subscription subscription);

    Optional<Subscription> findById(UUID id);

    List<Subscription> findByStatus(SubStatus status);

    List<Subscription> findByUserGuestId(UUID userGuestId);

    List<Subscription> findByAccountId(UUID accountId);

    /**
     * Anti-overbooking check (BR-06).
     * Returns true if there is already an active subscription for the given
     * account.
     */
    boolean existsActiveByAccountId(UUID accountId);

    /**
     * Dashboard master view (CU-A07).
     * Returns a flat projection joining subscriptions, accounts, and products.
     */
    List<SubscriptionDashboardDTO> findDashboard();
}
