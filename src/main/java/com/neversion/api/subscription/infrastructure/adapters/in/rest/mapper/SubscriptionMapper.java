package com.neversion.api.subscription.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.subscription.domain.model.Subscription;
import com.neversion.api.subscription.infrastructure.adapters.in.rest.dto.CreateSubscriptionRequest;
import com.neversion.api.subscription.infrastructure.adapters.in.rest.dto.SubscriptionResponse;

@Component
public class SubscriptionMapper {

    public Subscription toDomain(CreateSubscriptionRequest request) {
        if (request == null) return null;

        // UUIDs from request are resolved internally in the use case —
        // we pass them through as lookup keys; the service resolves Long IDs
        return Subscription.builder()
                .profileUuid(request.profileId())
                .clientUuid(request.clientId())
                .accountUuid(request.accountId())
                .purchaseDate(request.purchaseDate())
                .paymentDueDate(request.paymentDueDate())
                .price(request.price())
                .notes(request.notes())
                .build();
    }

    public SubscriptionResponse toResponse(Subscription subscription) {
        return subscription != null ? SubscriptionResponse.builder()
                .id(subscription.getUuid())
                .profileId(subscription.getProfileUuid())
                .clientId(subscription.getClientUuid())
                .accountId(subscription.getAccountUuid())
                .status(subscription.getStatus())
                .purchaseDate(subscription.getPurchaseDate())
                .paymentDueDate(subscription.getPaymentDueDate())
                .price(subscription.getPrice())
                .notes(subscription.getNotes())
                .createdAt(subscription.getCreatedAt())
                .build() : null;
    }
}
