package com.neversion.api.userguest.application.service;

import org.springframework.stereotype.Service;

import com.neversion.api.userguest.application.port.in.CreateUserGuestUseCase;
import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.domain.port.out.UserGuestRepositoryPort;

@Service
public class CreateUserGuestService implements CreateUserGuestUseCase {
    private final UserGuestRepositoryPort userGuestRepositoryPort;

    public CreateUserGuestService(UserGuestRepositoryPort userGuestRepositoryPort) {
        this.userGuestRepositoryPort = userGuestRepositoryPort;
    }

    @Override
    public UserGuest create(UserGuest userGuest) {
        return userGuestRepositoryPort.save(userGuest);
    }
}
