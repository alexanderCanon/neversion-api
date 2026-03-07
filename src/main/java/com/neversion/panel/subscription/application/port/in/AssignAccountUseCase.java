package com.neversion.panel.subscription.application.port.in;

import com.neversion.panel.subscription.domain.model.Subscription;

public interface AssignAccountUseCase {
    Subscription assign(Subscription subscription);
}
