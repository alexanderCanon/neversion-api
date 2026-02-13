package com.neversion.panel.sservicedetail.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.sservicedetail.domain.model.SserviceDetail;
import com.neversion.panel.sservicedetail.domain.port.out.SserviceDetailRepositoryPort;
import com.neversion.panel.sservicedetail.infrastructure.adapters.out.mapper.SserviceDetailPersistenceMapper;

@Repository
public class JpaSserviceDetailAdapter implements SserviceDetailRepositoryPort {
    private final SserviceDetailRepositoryAdapter sserviceDetailRepositoryAdapter;
    private final SserviceDetailPersistenceMapper sserviceDetailPersistenceMapper;

    public JpaSserviceDetailAdapter(SserviceDetailRepositoryAdapter sserviceDetailRepositoryAdapter,
        SserviceDetailPersistenceMapper sserviceDetailPersistenceMapper) {
        this.sserviceDetailRepositoryAdapter = sserviceDetailRepositoryAdapter;
        this.sserviceDetailPersistenceMapper = sserviceDetailPersistenceMapper;
    }

    @Override
    public SserviceDetail save(SserviceDetail sserviceDetail) {
        SserviceDetailEntity entity = sserviceDetailPersistenceMapper.toEntity(sserviceDetail);
        SserviceDetailEntity saved = sserviceDetailRepositoryAdapter.saveAndFlush(entity);
        SserviceDetailEntity loaded = sserviceDetailRepositoryAdapter.findById(saved.getId())
            .orElseThrow();
        return sserviceDetailPersistenceMapper.toDomain(loaded);
    }

    @Override
    public Optional<SserviceDetail> findById(Long id) {
        return sserviceDetailRepositoryAdapter.findById(id)
            .map(sserviceDetailPersistenceMapper::toDomain);
    }

    @Override
    public List<SserviceDetail> findAll() {
        return sserviceDetailRepositoryAdapter.findAll()
            .stream()
            .map(sserviceDetailPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<SserviceDetail> findByServiceName(String serviceName) {
        return sserviceDetailRepositoryAdapter.findByServiceName(serviceName)
            .stream()
            .map(sserviceDetailPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<SserviceDetail> findByCategoryName(String categoryName) {
        return sserviceDetailRepositoryAdapter.findByCategoryName(categoryName)
            .stream()
            .map(sserviceDetailPersistenceMapper::toDomain)
            .toList();
    }
}
