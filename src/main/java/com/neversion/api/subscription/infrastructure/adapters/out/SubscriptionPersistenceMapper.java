package com.neversion.api.subscription.infrastructure.adapters.out;

import org.springframework.stereotype.Component;

import com.neversion.api.account.infrastructure.adapters.out.AccountEntity;
import com.neversion.api.subscription.domain.model.Subscription;

@Component
public class SubscriptionPersistenceMapper {

    public Subscription toDomain(SubscriptionEntity entity) {
        return entity != null ? Subscription.builder()
                .id(entity.getId())
                .userGuestId(entity.getUserGuestId())
                .accountId(entity.getAccountId())
                .accountSlotId(entity.getAccountSlotId())
                .orderId(entity.getOrderId())
                .purchaseDate(entity.getPurchaseDate())
                .renewalDate(entity.getRenewalDate())
                .status(entity.getStatus())
                .build() : null;
    }

    public SubscriptionEntity toEntity(Subscription subscription) {
        if (subscription == null) {
            return null;
        }

        AccountEntity accountRef = AccountEntity.builder()
                .id(subscription.getAccountId())
                .build();

        return SubscriptionEntity.builder()
                .id(subscription.getId())
                .userGuestId(subscription.getUserGuestId())
                .accountId(subscription.getAccountId())
                .account(accountRef)
                .accountSlotId(subscription.getAccountSlotId())
                .orderId(subscription.getOrderId())
                .purchaseDate(subscription.getPurchaseDate())
                .renewalDate(subscription.getRenewalDate())
                .status(subscription.getStatus())
                .build();
    }
}
