package com.neversion.api.accountslot.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.accountslot.domain.model.AccountSlot;
import com.neversion.api.accountslot.infrastructure.adapters.out.AccountSlotEntity;

@Component
public class AccountSlotPersistenceMapper {

    public AccountSlot toDomain(AccountSlotEntity entity) {
        return entity != null ? AccountSlot.builder()
                .id(entity.getId())
                .accountId(entity.getAccountId())
                .profileName(entity.getProfileName())
                .pin(entity.getPin())
                .status(entity.getStatus())
                .build() : null;
    }

    public AccountSlotEntity toEntity(AccountSlot slot) {
        return slot != null ? AccountSlotEntity.builder()
                .id(slot.getId())
                .accountId(slot.getAccountId())
                .profileName(slot.getProfileName())
                .pin(slot.getPin())
                .status(slot.getStatus())
                .build() : null;
    }
}
