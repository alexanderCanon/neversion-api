package com.neversion.panel.profile.application.port.in;

import java.util.UUID;

public interface DeactivateProfileUseCase {
    void deactivate(UUID id);
}
