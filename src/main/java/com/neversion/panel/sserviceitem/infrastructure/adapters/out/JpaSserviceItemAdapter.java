package com.neversion.panel.sserviceitem.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.sserviceitem.domain.model.SserviceItem;
import com.neversion.panel.sserviceitem.domain.port.out.SserviceItemRepositoryPort;
import com.neversion.panel.sserviceitem.infrastructure.adapters.out.mapper.SserviceItemPersistenceMapper;

@Repository
public class JpaSserviceItemAdapter implements SserviceItemRepositoryPort {

    private final SserviceItemRepositoryAdapter itemRepo;
    private final SserviceItemPersistenceMapper itemPersistenceMapper;

    public JpaSserviceItemAdapter(SserviceItemRepositoryAdapter itemRepo,
            SserviceItemPersistenceMapper itemPersistenceMapper) {
        this.itemRepo = itemRepo;
        this.itemPersistenceMapper = itemPersistenceMapper;
    }

    @Override
    public SserviceItem save(SserviceItem item) {
        SserviceItemEntity entity = itemPersistenceMapper.toEntity(item);
        SserviceItemEntity saved = itemRepo.saveAndFlush(entity);
        SserviceItemEntity loaded = itemRepo.findById(saved.getId())
                .orElseThrow();
        return itemPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<SserviceItem> findById(Long id) {
        return itemRepo.findById(id)
                .map(itemPersistenceMapper::toDomain);
    }

    @Override
    public List<SserviceItem> findAll() {
        return itemRepo.findAll()
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<SserviceItem> findByServiceName(String serviceName) {
        return itemRepo.findByServiceName(serviceName)
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<SserviceItem> findByAccountType(String accountType) {
        return itemRepo.findByAccountType(accountType)
                .stream()
                .map(itemPersistenceMapper::toDomain)
                .toList();
    }
}
