package com.neversion.panel.userguest.application.port.in;

import java.util.List;
import java.util.UUID;

import com.neversion.panel.userguest.domain.model.UserGuest;

public interface GetUserGuestUseCase {
    UserGuest getById(UUID id);
    List<UserGuest> getByName(String name);
    List<UserGuest> getByPhone(String phone);
    List<UserGuest> getAll();
}
