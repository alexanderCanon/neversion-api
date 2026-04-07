package com.neversion.api.userguest.infrastructure.adapters.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.domain.port.out.UserGuestRepositoryPort;
import com.neversion.api.userguest.infrastructure.adapters.out.mapper.UserGuestPersistenceMapper;

@Repository
public class JpaUserGuestAdapter implements UserGuestRepositoryPort {
    private final SpringDataUserGuestRepository userGuestRepo;
    private final UserGuestPersistenceMapper userGuestMapper;

    public JpaUserGuestAdapter(SpringDataUserGuestRepository userGuestRepo,
            UserGuestPersistenceMapper userGuestMapper) {
        this.userGuestRepo = userGuestRepo;
        this.userGuestMapper = userGuestMapper;
    }

    @Override
    public UserGuest save(UserGuest userGuest) {
        UserGuestEntity entity = userGuestMapper.toEntity(userGuest);
        UserGuestEntity saved = userGuestRepo.save(entity);
        return userGuestMapper.toDomain(saved);
    }

    @Override
    public Optional<UserGuest> findById(UUID id) {
        return userGuestRepo.findById(id)
                .map(userGuestMapper::toDomain);
    }

    @Override
    public List<UserGuest> findByName(String name) {
        return userGuestRepo.findByName(name)
                .stream()
                .map(userGuestMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserGuest> findByPhone(String phone) {
        return userGuestRepo.findByPhone(phone)
                .stream()
                .map(userGuestMapper::toDomain)
                .toList();
    }

    @Override
    public List<UserGuest> findAll() {
        return userGuestRepo.findAll()
                .stream()
                .map(userGuestMapper::toDomain)
                .toList();
    }

    @Override
    public void deactivate(UUID id) {
        userGuestRepo.deactivate(id);
    }
}
