package com.neversion.panel.userguest.application.port.in;

import java.util.UUID;

public interface DeactivateUserGuestUseCase {
    void deactivate(UUID id);
}
