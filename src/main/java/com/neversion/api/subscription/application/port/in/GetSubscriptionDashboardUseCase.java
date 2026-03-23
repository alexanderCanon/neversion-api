package com.neversion.api.subscription.application.port.in;

import java.util.List;

import com.neversion.api.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

public interface GetSubscriptionDashboardUseCase {
    List<SubscriptionDashboardDTO> getDashboard();
}
