package com.neversion.panel.userguest.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.out.UserGuestEntity;

@Component
public class UserGuestPersistenceMapper {

    public UserGuest toDomain(UserGuestEntity entity) {
        return new UserGuest(
            entity.getId(),
            entity.getName(),
            entity.getEmail(),
            entity.getPhone(),
            entity.getIsActive(),
            entity.getCreatedAt()
        );
    }

    public UserGuestEntity toEntity(UserGuest userGuest) {
        return new UserGuestEntity(
            userGuest.id(),
            userGuest.name(),
            userGuest.email(),
            userGuest.phone(),
            userGuest.isActive()
        );
    }
}
