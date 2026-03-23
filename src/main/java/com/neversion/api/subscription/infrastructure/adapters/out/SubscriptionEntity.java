package com.neversion.api.subscription.infrastructure.adapters.out;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.api.account.infrastructure.adapters.out.AccountEntity;
import com.neversion.api.infrastructure.AuditableEntity;
import com.neversion.api.subscription.domain.model.enums.SubStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@Builder
public class SubscriptionEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_guest_id")
    private UUID userGuestId;

    @Column(name = "account_id", insertable = false, updatable = false)
    private UUID accountId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "renewal_date", nullable = false)
    private LocalDate renewalDate;

    @Column(name = "account_slot_id")
    private UUID accountSlotId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "status", nullable = false, columnDefinition = "sub_status")
    private SubStatus status;

    public SubscriptionEntity() {
    }

    public SubscriptionEntity(UUID id, UUID userGuestId, UUID accountId, AccountEntity account,
            LocalDate purchaseDate, LocalDate renewalDate, UUID accountSlotId, UUID orderId,
            SubStatus status) {
        this.id = id;
        this.userGuestId = userGuestId;
        this.accountId = accountId;
        this.account = account;
        this.accountId = account != null ? account.getId() : accountId;
        this.purchaseDate = purchaseDate;
        this.renewalDate = renewalDate;
        this.accountSlotId = accountSlotId;
        this.orderId = orderId;
        this.status = status;
    }
}
