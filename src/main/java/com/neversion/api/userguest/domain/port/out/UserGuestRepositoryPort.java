package com.neversion.api.userguest.domain.port.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.neversion.api.userguest.domain.model.UserGuest;

public interface UserGuestRepositoryPort {
    UserGuest save(UserGuest userGuest);
    Optional<UserGuest> findById(UUID id);
    List<UserGuest> findByName(String name);
    List<UserGuest> findByPhone(String phone);
    List<UserGuest> findAll();
    void deactivate(UUID id);
}
