package com.neversion.panel.accountslot.domain.model;

import java.util.UUID;

import com.neversion.panel.accountslot.domain.model.enums.SlotStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AccountSlot {

    private UUID id;
    private UUID accountId;
    private String profileName;
    private String pin;
    private SlotStatus status;

    public AccountSlot() {
    }

    public AccountSlot(UUID id, UUID accountId, String profileName, String pin, SlotStatus status) {
        this.id = id;
        this.accountId = accountId;
        this.profileName = profileName;
        this.pin = pin;
        this.status = status;
    }
}
