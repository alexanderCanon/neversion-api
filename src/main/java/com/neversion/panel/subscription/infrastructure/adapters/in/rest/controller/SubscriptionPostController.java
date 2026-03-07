package com.neversion.panel.subscription.infrastructure.adapters.in.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neversion.panel.subscription.application.port.in.AssignAccountUseCase;
import com.neversion.panel.subscription.domain.model.Subscription;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.CreateSubscriptionRequest;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionResponse;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.mapper.SubscriptionMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Subscriptions", description = "Subscription describes the relationship between a customer and a product.")
public class SubscriptionPostController {

    private final AssignAccountUseCase assignAccountUseCase;
    private final SubscriptionMapper subscriptionMapper;

    public SubscriptionPostController(AssignAccountUseCase assignAccountUseCase,
            SubscriptionMapper subscriptionMapper) {
        this.assignAccountUseCase = assignAccountUseCase;
        this.subscriptionMapper = subscriptionMapper;
    }

    @PostMapping
    @Operation(summary = "Assign account to subscription", description = "Manually assigns an account to a customer order, creating a subscription. "
            + "Validates individual account exclusivity (BR-06) before saving.")
    @ApiResponse(responseCode = "201", description = "Subscription created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request data")
    @ApiResponse(responseCode = "404", description = "Account not found")
    @ApiResponse(responseCode = "409", description = "Individual account already has an active subscription")
    public ResponseEntity<SubscriptionResponse> assignAccount(
            @Valid @RequestBody CreateSubscriptionRequest request) {
        Subscription subscription = subscriptionMapper.toDomain(request);
        Subscription created = assignAccountUseCase.assign(subscription);
        SubscriptionResponse response = subscriptionMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
