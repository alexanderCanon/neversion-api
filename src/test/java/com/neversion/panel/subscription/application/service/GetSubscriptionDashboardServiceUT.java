package com.neversion.panel.subscription.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.subscription.domain.port.out.SubscriptionRepositoryPort;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetSubscriptionDashboardService unit tests")
class GetSubscriptionDashboardServiceUT {

    @Mock
    private SubscriptionRepositoryPort subscriptionRepositoryPort;

    private GetSubscriptionDashboardService getSubscriptionDashboardService;

    @BeforeEach
    void setUp() {
        getSubscriptionDashboardService = new GetSubscriptionDashboardService(subscriptionRepositoryPort);
    }

    @Nested
    @DisplayName("getDashboard")
    class GetDashboard {

        @Test
        @DisplayName("getDashboard - should delegate to repository and return results")
        void getDashboard_shouldDelegateToRepositoryAndReturnResults() {
            // Given
            List<SubscriptionDashboardDTO> dashboardData = List.of();
            when(subscriptionRepositoryPort.findDashboard()).thenReturn(dashboardData);

            // When
            List<SubscriptionDashboardDTO> result = getSubscriptionDashboardService.getDashboard();

            // Then
            assertThat(result).isNotNull();
            verify(subscriptionRepositoryPort).findDashboard();
        }
    }
}
