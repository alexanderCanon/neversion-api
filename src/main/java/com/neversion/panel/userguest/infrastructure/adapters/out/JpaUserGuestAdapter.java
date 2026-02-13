package com.neversion.panel.userguest.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.domain.port.out.UserGuestRepositoryPort;
import com.neversion.panel.userguest.infrastructure.adapters.out.mapper.UserGuestPersistenceMapper;

@Repository
public class JpaUserGuestAdapter implements UserGuestRepositoryPort {
    private final UserGuestRepositoryAdapter userGuestRepositoryAdapter;
    private final UserGuestPersistenceMapper userGuestPersistenceMapper;

    public JpaUserGuestAdapter(UserGuestRepositoryAdapter userGuestRepositoryAdapter,
        UserGuestPersistenceMapper userGuestPersistenceMapper) {
        this.userGuestRepositoryAdapter = userGuestRepositoryAdapter;
        this.userGuestPersistenceMapper = userGuestPersistenceMapper;
    }

    @Override
    public UserGuest save(UserGuest userGuest) {
        UserGuestEntity entity = userGuestPersistenceMapper.toEntity(userGuest);
        UserGuestEntity saved = userGuestRepositoryAdapter.save(entity);
        return userGuestPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<UserGuest> findById(UUID id) {
        return userGuestRepositoryAdapter.findById(id)
            .map(userGuestPersistenceMapper::toDomain);
    }

    @Override
    public List<UserGuest> findByName(String name) {
        return userGuestRepositoryAdapter.findByName(name)
            .stream()
            .map(userGuestPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<UserGuest> findByPhone(String phone) {
        return userGuestRepositoryAdapter.findByPhone(phone)
            .stream()
            .map(userGuestPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public List<UserGuest> findAll() {
        return userGuestRepositoryAdapter.findAll()
            .stream()
            .map(userGuestPersistenceMapper::toDomain)
            .toList();
    }

    @Override
    public void deactivate(UUID id) {
        userGuestRepositoryAdapter.deactivate(id);
    }
}
