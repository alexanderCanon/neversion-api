package com.neversion.panel.subscription.infrastructure.adapters.in.rest.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.subscription.application.port.in.UpdateSubscriptionUseCase;
import com.neversion.panel.subscription.domain.model.Subscription;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionResponse;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.mapper.SubscriptionMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription lifecycle management (CU-A06)")
public class SubscriptionPutController {

    private final UpdateSubscriptionUseCase updateSubscriptionUseCase;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionPutController(
            UpdateSubscriptionUseCase updateSubscriptionUseCase,
            SubscriptionMapper subscriptionMapper) {
        this.updateSubscriptionUseCase = updateSubscriptionUseCase;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PutMapping("/{id}/suspend")
    @Operation(summary = "Suspend a subscription", description = "Transitions an ACTIVE subscription to SUSPENDED.")
    @ApiResponse(responseCode = "200", description = "Subscription suspended")
    @ApiResponse(responseCode = "400", description = "Subscription is not ACTIVE")
    @ApiResponse(responseCode = "404", description = "Subscription not found")
    public ResponseEntity<SubscriptionResponse> suspend(
            @Parameter(description = "Subscription UUID") @PathVariable UUID id) {

        Subscription suspended = updateSubscriptionUseCase.suspend(id);
        return ResponseEntity.ok(subscriptionMapper.toResponse(suspended));
    }

    @PutMapping("/{id}/terminate")
    @Operation(summary = "Terminate a subscription", description = "Transitions a subscription to CANCELLED.")
    @ApiResponse(responseCode = "200", description = "Subscription terminated")
    @ApiResponse(responseCode = "400", description = "Subscription is already cancelled")
    @ApiResponse(responseCode = "404", description = "Subscription not found")
    public ResponseEntity<SubscriptionResponse> terminate(
            @Parameter(description = "Subscription UUID") @PathVariable UUID id) {

        Subscription terminated = updateSubscriptionUseCase.terminate(id);
        return ResponseEntity.ok(subscriptionMapper.toResponse(terminated));
    }
}
