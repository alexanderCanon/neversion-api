package com.neversion.panel.sservice.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.port.out.SserviceRepositoryPort;
import com.neversion.panel.sservice.infrastructure.adapters.out.mapper.SservicePersistenceMapper;

@Repository
public class JpaSserviceAdapter implements SserviceRepositoryPort {

    private final SserviceRepositoryAdapter sserviceRepo;
    private final SservicePersistenceMapper sservicePersistenceMapper;

    public JpaSserviceAdapter(SserviceRepositoryAdapter sserviceRepo,
            SservicePersistenceMapper sservicePersistenceMapper) {
        this.sserviceRepo = sserviceRepo;
        this.sservicePersistenceMapper = sservicePersistenceMapper;
    }

    @Override
    public Sservice save(Sservice sservice) {
        SserviceEntity sserviceEntity = sservicePersistenceMapper.toEntity(sservice);
        SserviceEntity savedPlatform = sserviceRepo.save(sserviceEntity);
        return sservicePersistenceMapper.toDomain(savedPlatform);
    }

    @Override
    public List<Sservice> findAll() {
        return sserviceRepo.findAll()
                .stream()
                .map(sservicePersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Sservice> findById(Integer id) {
        return sserviceRepo.findById(id)
                .map(sservicePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Sservice> findByName(String name) {
        return sserviceRepo.findByName(name)
                .map(sservicePersistenceMapper::toDomain);
    }

    // @Override
    // public List<Sservice> findByCategory() {
    // return sserviceRepositoryAdapter.findByCategory()
    // .stream()
    // .map(sservicePersistenceMapper::toDomain)
    // .toList();
    // }

}
