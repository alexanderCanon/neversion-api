package com.neversion.api.userguest.application.port.in;

import java.util.UUID;

public interface DeactivateUserGuestUseCase {
    void deactivate(UUID id);
}
