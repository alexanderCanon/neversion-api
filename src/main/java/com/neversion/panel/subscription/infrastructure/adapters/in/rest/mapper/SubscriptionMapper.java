package com.neversion.panel.subscription.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.subscription.domain.model.Subscription;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.CreateSubscriptionRequest;
import com.neversion.panel.subscription.infrastructure.adapters.in.rest.dto.SubscriptionResponse;

@Component
public class SubscriptionMapper {

    public Subscription toDomain(CreateSubscriptionRequest request) {
        return request != null ? Subscription.builder()
                .accountId(request.accountId())
                .userGuestId(request.userGuestId())
                .purchaseDate(request.purchaseDate())
                .renewalDate(request.renewalDate())
                .accountSlotId(request.accountSlotId())
                .orderId(request.orderId())
                .build() : null;
    }

    public SubscriptionResponse toResponse(Subscription subscription) {
        return subscription != null ? SubscriptionResponse.builder()
                .id(subscription.getId())
                .accountId(subscription.getAccountId())
                .userGuestId(subscription.getUserGuestId())
                .accountSlotId(subscription.getAccountSlotId())
                .orderId(subscription.getOrderId())
                .purchaseDate(subscription.getPurchaseDate())
                .renewalDate(subscription.getRenewalDate())
                .status(subscription.getStatus())
                .build() : null;
    }
}
