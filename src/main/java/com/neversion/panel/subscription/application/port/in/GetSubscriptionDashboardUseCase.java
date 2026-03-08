package com.neversion.panel.subscription.application.port.in;

import java.util.List;

import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

public interface GetSubscriptionDashboardUseCase {
    List<SubscriptionDashboardDTO> getDashboard();
}
