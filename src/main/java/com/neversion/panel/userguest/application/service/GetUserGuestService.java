package com.neversion.panel.userguest.application.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.userguest.application.port.in.GetUserGuestUseCase;
import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.domain.port.out.UserGuestRepositoryPort;

@Service
public class GetUserGuestService implements GetUserGuestUseCase {
    private final UserGuestRepositoryPort userGuestRepositoryPort;

    public GetUserGuestService(UserGuestRepositoryPort userGuestRepositoryPort) {
        this.userGuestRepositoryPort = userGuestRepositoryPort;
    }

    @Override
    public UserGuest getById(UUID id) {
        return userGuestRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserGuest with id " + id + " not found"));
    }

    @Override
    public List<UserGuest> getByName(String name) {
        return userGuestRepositoryPort.findByName(name);
    }

    @Override
    public List<UserGuest> getByPhone(String phone) {
        return userGuestRepositoryPort.findByPhone(phone);
    }

    @Override
    public List<UserGuest> getAll() {
        return userGuestRepositoryPort.findAll();
    }
}
