package com.neversion.api.userguest.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.neversion.api.userguest.application.port.in.UpdateUserGuestUseCase;
import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.domain.port.out.UserGuestRepositoryPort;

@Service
public class UpdateUserGuestService implements UpdateUserGuestUseCase {

    private final UserGuestRepositoryPort userGuestRepositoryPort;

    public UpdateUserGuestService(UserGuestRepositoryPort userGuestRepositoryPort) {
        this.userGuestRepositoryPort = userGuestRepositoryPort;
    }

    @Override
    @Transactional
    public UserGuest update(UserGuest userGuest) {
        UserGuest existing = userGuestRepositoryPort.findById(userGuest.getId())
                .orElseThrow(() -> new RuntimeException("UserGuest not found with id: " + userGuest.getId()));

        existing.setName(userGuest.getName());
        existing.setEmail(userGuest.getEmail());
        existing.setPhone(userGuest.getPhone());

        return userGuestRepositoryPort.save(existing);
    }
}
