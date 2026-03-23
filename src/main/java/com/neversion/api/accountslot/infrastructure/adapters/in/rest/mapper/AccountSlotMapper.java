package com.neversion.api.accountslot.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.accountslot.domain.model.AccountSlot;
import com.neversion.api.accountslot.infrastructure.adapters.in.rest.dto.AccountSlotResponse;

@Component
public class AccountSlotMapper {

    public AccountSlotResponse toResponse(AccountSlot slot) {
        return slot != null ? AccountSlotResponse.builder()
                .id(slot.getId())
                .accountId(slot.getAccountId())
                .profileName(slot.getProfileName())
                .pin(slot.getPin())
                .status(slot.getStatus().name())
                .build() : null;
    }
}
