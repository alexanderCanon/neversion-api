package com.neversion.api.accountslot.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.api.accountslot.domain.model.AccountSlot;

public interface AccountSlotRepositoryPort {

    AccountSlot save(AccountSlot slot);

    Optional<AccountSlot> findById(UUID id);

    List<AccountSlot> findByAccountId(UUID accountId);

    List<AccountSlot> findAvailableByAccountId(UUID accountId);

    void saveAll(List<AccountSlot> slots);
}
