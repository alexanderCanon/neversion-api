package com.neversion.api.subscription.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.subscription.domain.model.enums.SubStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Subscription {

    private UUID id;
    private UUID userGuestId;
    private UUID accountId;
    private UUID accountSlotId;
    private UUID orderId;
    private LocalDate purchaseDate;
    private LocalDate renewalDate;
    private SubStatus status;

    public Subscription() {
    }

    public Subscription(UUID id, UUID userGuestId, UUID accountId, UUID accountSlotId, UUID orderId,
            LocalDate purchaseDate, LocalDate renewalDate, SubStatus status) {
        this.id = id;
        this.userGuestId = userGuestId;
        this.accountId = accountId;
        this.accountSlotId = accountSlotId;
        this.orderId = orderId;
        this.purchaseDate = purchaseDate;
        this.renewalDate = renewalDate;
        this.status = status;
    }
}
