package com.neversion.panel.userguest.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.userguest.application.port.in.DeactivateUserGuestUseCase;
import com.neversion.panel.userguest.domain.port.out.UserGuestRepositoryPort;

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
