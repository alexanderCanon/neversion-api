package com.neversion.api.subscription.application.port.in;

import com.neversion.api.subscription.domain.model.Subscription;

public interface AssignAccountUseCase {
    Subscription assign(Subscription subscription);
}
