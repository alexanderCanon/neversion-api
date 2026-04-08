package com.neversion.api.accountslot.domain.model;

import java.util.UUID;

import com.neversion.api.accountslot.domain.model.enums.SlotStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountSlot {

    private UUID id;
    private UUID accountId;
    private String profileName;
    private String pin;
    private SlotStatus status;
}
