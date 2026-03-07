package com.neversion.panel.accountslot.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.panel.accountslot.application.port.in.AccountSlotUseCase;
import com.neversion.panel.accountslot.domain.model.AccountSlot;
import com.neversion.panel.accountslot.domain.model.enums.SlotStatus;
import com.neversion.panel.accountslot.domain.port.out.AccountSlotRepositoryPort;

@Service
public class AccountSlotService implements AccountSlotUseCase {

    private final AccountSlotRepositoryPort accountSlotRepositoryPort;

    public AccountSlotService(AccountSlotRepositoryPort accountSlotRepositoryPort) {
        this.accountSlotRepositoryPort = accountSlotRepositoryPort;
    }

    @Override
    @Transactional
    public AccountSlot save(AccountSlot slot) {
        return accountSlotRepositoryPort.save(slot);
    }

    @Override
    public Optional<AccountSlot> findById(UUID id) {
        return accountSlotRepositoryPort.findById(id);
    }

    @Override
    public List<AccountSlot> findByAccountId(UUID accountId) {
        return accountSlotRepositoryPort.findByAccountId(accountId);
    }

    @Override
    public List<AccountSlot> findAvailableByAccountId(UUID accountId) {
        return accountSlotRepositoryPort.findAvailableByAccountId(accountId);
    }

    @Override
    @Transactional
    public void generateSlotsForAccount(UUID accountId, int count) {
        List<AccountSlot> slots = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            slots.add(AccountSlot.builder()
                    .accountId(accountId)
                    .status(SlotStatus.AVAILABLE)
                    .build());
        }
        accountSlotRepositoryPort.saveAll(slots);
    }
}
