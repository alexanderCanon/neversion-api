package com.neversion.api.subscription.application.port.in;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.subscription.domain.model.Subscription;

public interface RenewSubscriptionUseCase {

    /**
     * US-045: Renews an active or suspended subscription owned by the caller.
     */
    Subscription renew(UUID subscriptionUuid, String callerExternalId);

    /**
     * US-045 explicit-date renewal: renews using a seller-provided due date
     * instead of the BR-07 computation (used for late renewals past grace).
     */
    Subscription renew(UUID subscriptionUuid, LocalDate explicitDueDate, String callerExternalId);
}
