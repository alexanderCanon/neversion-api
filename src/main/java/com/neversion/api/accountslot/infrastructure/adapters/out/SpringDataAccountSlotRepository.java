package com.neversion.api.accountslot.infrastructure.adapters.out;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neversion.api.accountslot.domain.model.enums.SlotStatus;

public interface SpringDataAccountSlotRepository extends JpaRepository<AccountSlotEntity, UUID> {

    List<AccountSlotEntity> findByAccountId(UUID accountId);

    List<AccountSlotEntity> findByAccountIdAndStatus(UUID accountId, SlotStatus status);
}
