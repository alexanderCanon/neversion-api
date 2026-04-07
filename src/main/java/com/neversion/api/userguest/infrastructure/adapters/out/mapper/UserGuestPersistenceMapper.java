package com.neversion.api.userguest.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.api.userguest.domain.model.UserGuest;
import com.neversion.api.userguest.infrastructure.adapters.out.UserGuestEntity;

@Component
public class UserGuestPersistenceMapper {

    public UserGuest toDomain(UserGuestEntity entity) {
        return entity != null ? UserGuest.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build() : null;
    }

    public UserGuestEntity toEntity(UserGuest domain) {
        return domain != null ? UserGuestEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail())
                .phone(domain.getPhone())
                .build() : null;
    }
}
