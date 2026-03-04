package com.neversion.panel.subscription.domain.model;

import java.time.LocalDate;
import java.util.UUID;

import com.neversion.panel.account.domain.model.Account;
import com.neversion.panel.subscription.domain.model.enums.SubStatus;
import com.neversion.panel.userguest.domain.model.UserGuest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class Subscription {

    private UUID id;
    private UserGuest userGuest;
    private Account account;
    private String userGuestName;
    private LocalDate purchaseDate;
    private LocalDate renewalDate;
    private String profile;
    private String pin;
    private SubStatus status;

    public Subscription() {
    }

    public Subscription(UUID id, UserGuest userGuest, Account account, String userGuestName, LocalDate purchaseDate,
            LocalDate renewalDate, String profile, String pin, SubStatus status) {
        this.id = id;
        this.userGuest = userGuest;
        this.account = account;
        this.userGuestName = userGuestName;
        this.purchaseDate = purchaseDate;
        this.renewalDate = renewalDate;
        this.profile = profile;
        this.pin = pin;
        this.status = status;
    }
}
