package com.neversion.panel.subscription.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.neversion.panel.subscription.application.port.in.GetSubscriptionDashboardUseCase;
import com.neversion.panel.subscription.domain.port.out.SubscriptionRepositoryPort;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

@Service
public class GetSubscriptionDashboardService implements GetSubscriptionDashboardUseCase {

    private final SubscriptionRepositoryPort subscriptionRepositoryPort;

    public GetSubscriptionDashboardService(SubscriptionRepositoryPort subscriptionRepositoryPort) {
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
    }

    @Override
    public List<SubscriptionDashboardDTO> getDashboard() {
        return subscriptionRepositoryPort.findDashboard();
    }
}
