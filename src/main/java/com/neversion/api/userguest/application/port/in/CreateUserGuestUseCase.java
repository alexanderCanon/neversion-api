package com.neversion.api.userguest.application.port.in;

import com.neversion.api.userguest.domain.model.UserGuest;

public interface CreateUserGuestUseCase {
    UserGuest create(UserGuest userGuest);
}
