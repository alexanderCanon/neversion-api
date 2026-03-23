package com.neversion.api.accountslot.application.port.in;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.api.accountslot.domain.model.AccountSlot;

public interface AccountSlotUseCase {

    AccountSlot save(AccountSlot slot);

    Optional<AccountSlot> findById(UUID id);

    List<AccountSlot> findByAccountId(UUID accountId);

    List<AccountSlot> findAvailableByAccountId(UUID accountId);

    /**
     * Auto-generates N slots for the given account.
     */
    void generateSlotsForAccount(UUID accountId, int count);
}
