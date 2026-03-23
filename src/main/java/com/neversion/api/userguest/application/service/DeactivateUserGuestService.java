package com.neversion.api.userguest.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.userguest.application.port.in.DeactivateUserGuestUseCase;
import com.neversion.api.userguest.domain.port.out.UserGuestRepositoryPort;

@Service
public class DeactivateUserGuestService implements DeactivateUserGuestUseCase {
    private final UserGuestRepositoryPort userGuestRepositoryPort;

    public DeactivateUserGuestService(UserGuestRepositoryPort userGuestRepositoryPort) {
        this.userGuestRepositoryPort = userGuestRepositoryPort;
    }

    @Override
    public void deactivate(UUID id) {
        userGuestRepositoryPort.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("UserGuest with id " + id + " not found"));
        userGuestRepositoryPort.deactivate(id);
    }
}
