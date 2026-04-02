package com.neversion.api.userguest.application.port.in;

import com.neversion.api.userguest.domain.model.UserGuest;

public interface UpdateUserGuestUseCase {
    UserGuest update(UserGuest userGuest);
}
