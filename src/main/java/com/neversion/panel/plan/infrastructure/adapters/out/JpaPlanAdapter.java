package com.neversion.panel.plan.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.plan.domain.model.Plan;
import com.neversion.panel.plan.domain.port.out.PlanRepositoryPort;
import com.neversion.panel.plan.infrastructure.adapters.out.mapper.PlanPersistenceMapper;

@Repository
public class JpaPlanAdapter implements PlanRepositoryPort {

    private final PlanRepositoryAdapter itemRepo;
    private final PlanPersistenceMapper itemPersistenceMapper;

    public JpaPlanAdapter(PlanRepositoryAdapter itemRepo,
            PlanPersistenceMapper itemPersistenceMapper) {
        this.itemRepo = itemRepo;
        this.itemPersistenceMapper = itemPersistenceMapper;
    }

    @Override
    public Plan save(Plan item) {
        PlanEntity entity = itemPersistenceMapper.toEntity(item);
        PlanEntity saved = itemRepo.saveAndFlush(entity);
        PlanEntity loaded = itemRepo.findById(saved.getId())
                .orElseThrow();
        return itemPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<Plan> findById(Long id) {
        return itemRepo.findById(id)
                .map(itemPersistenceMapper::toDomain);
    }

    @Override
    public List<Plan> findAll() {
        return itemRepo.findAll()
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Plan> findByProductName(String productName) {
        return itemRepo.findByProduct_Name(productName)
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<Plan> findByAccountType(String accountType) {
        return itemRepo.findByAccountType(accountType)
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }
}
