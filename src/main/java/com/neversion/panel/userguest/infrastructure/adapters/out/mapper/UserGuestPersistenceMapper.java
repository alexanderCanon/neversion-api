package com.neversion.panel.userguest.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.infrastructure.adapters.out.UserGuestEntity;

@Component
public class UserGuestPersistenceMapper {

    public UserGuest toDomain(UserGuestEntity entity) {
        if (entity == null)
            return null;
        return UserGuest.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt() != null ? entity.getCreatedAt().toInstant() : null)
                .build();
    }

    public UserGuestEntity toEntity(UserGuest domain) {
        if (domain == null)
            return null;
        return UserGuestEntity.builder()
                .id(domain.id())
                .name(domain.name())
                .email(domain.email())
                .phone(domain.phone())
                .build();
    }
}
