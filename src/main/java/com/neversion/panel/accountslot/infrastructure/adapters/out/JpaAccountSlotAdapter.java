package com.neversion.panel.accountslot.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.panel.accountslot.domain.model.AccountSlot;
import com.neversion.panel.accountslot.domain.model.enums.SlotStatus;
import com.neversion.panel.accountslot.domain.port.out.AccountSlotRepositoryPort;
import com.neversion.panel.accountslot.infrastructure.adapters.out.mapper.AccountSlotPersistenceMapper;

@Repository
public class JpaAccountSlotAdapter implements AccountSlotRepositoryPort {

    private final SpringDataAccountSlotRepository springDataRepo;
    private final AccountSlotPersistenceMapper mapper;

    public JpaAccountSlotAdapter(SpringDataAccountSlotRepository springDataRepo,
            AccountSlotPersistenceMapper mapper) {
        this.springDataRepo = springDataRepo;
        this.mapper = mapper;
    }

    @Override
    public AccountSlot save(AccountSlot slot) {
        AccountSlotEntity entity = mapper.toEntity(slot);
        return mapper.toDomain(springDataRepo.save(entity));
    }

    @Override
    public Optional<AccountSlot> findById(UUID id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<AccountSlot> findByAccountId(UUID accountId) {
        return springDataRepo.findByAccountId(accountId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<AccountSlot> findAvailableByAccountId(UUID accountId) {
        return springDataRepo.findByAccountIdAndStatus(accountId, SlotStatus.AVAILABLE).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<AccountSlot> slots) {
        List<AccountSlotEntity> entities = slots.stream()
                .map(mapper::toEntity)
                .toList();
        springDataRepo.saveAll(entities);
    }
}
