package com.neversion.panel.userguest.application.port.in;

import com.neversion.panel.userguest.domain.model.UserGuest;

public interface CreateUserGuestUseCase {
    UserGuest create(UserGuest userGuest);
}
