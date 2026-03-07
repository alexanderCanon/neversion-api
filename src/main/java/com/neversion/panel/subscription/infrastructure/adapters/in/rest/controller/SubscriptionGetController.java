package com.neversion.panel.subscription.infrastructure.adapters.in.rest.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.subscription.application.port.in.GetSubscriptionDashboardUseCase;
import com.neversion.panel.subscription.domain.model.enums.SubStatus;
import com.neversion.panel.subscription.domain.port.out.SubscriptionRepositoryPort;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionDashboardDTO;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionResponse;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.mapper.SubscriptionMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription monitoring and dashboard (CU-A04, CU-A07)")
public class SubscriptionGetController {

    private final GetSubscriptionDashboardUseCase getSubscriptionDashboardUseCase;
    private final SubscriptionRepositoryPort subscriptionRepositoryPort;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionGetController(
            GetSubscriptionDashboardUseCase getSubscriptionDashboardUseCase,
            SubscriptionRepositoryPort subscriptionRepositoryPort,
            SubscriptionMapper subscriptionMapper) {
        this.getSubscriptionDashboardUseCase = getSubscriptionDashboardUseCase;
        this.subscriptionRepositoryPort = subscriptionRepositoryPort;
        this.subscriptionMapper = subscriptionMapper;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get subscription dashboard", description = "Returns the admin master view (CU-A07) with all subscriptions joined with "
            + "account credentials and product names.")
    @ApiResponse(responseCode = "200", description = "Dashboard data retrieved successfully")
    public ResponseEntity<List<SubscriptionDashboardDTO>> getDashboard() {
        List<SubscriptionDashboardDTO> dashboard = getSubscriptionDashboardUseCase.getDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @GetMapping
    @Operation(summary = "List subscriptions with filters", description = "CU-A04: Subscription monitoring. Filter by status, customer, or account.")
    @ApiResponse(responseCode = "200", description = "Filtered subscription list")
    public ResponseEntity<List<SubscriptionResponse>> listSubscriptions(
            @Parameter(description = "Filter by status (ACTIVE, EXPIRED, CANCELLED, SUSPENDED)") @RequestParam(required = false) SubStatus status,
            @Parameter(description = "Filter by guest user UUID") @RequestParam(required = false) UUID userGuestId,
            @Parameter(description = "Filter by account UUID") @RequestParam(required = false) UUID accountId) {

        List<SubscriptionResponse> responses;

        if (status != null) {
            responses = subscriptionRepositoryPort.findByStatus(status).stream()
                    .map(subscriptionMapper::toResponse)
                    .toList();
        } else if (userGuestId != null) {
            responses = subscriptionRepositoryPort.findByUserGuestId(userGuestId).stream()
                    .map(subscriptionMapper::toResponse)
                    .toList();
        } else if (accountId != null) {
            responses = subscriptionRepositoryPort.findByAccountId(accountId).stream()
                    .map(subscriptionMapper::toResponse)
                    .toList();
        } else {
            responses = List.of(); // require at least one filter
        }

        return ResponseEntity.ok(responses);
    }
}
