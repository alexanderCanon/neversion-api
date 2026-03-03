package com.neversion.panel.profile.infrastructure.adapters.out.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.profile.domain.model.Profile;
import com.neversion.panel.profile.infrastructure.adapters.out.ProfileEntity;

@Component
public class ProfilePersistenceMapper {

    public Profile toDomain(ProfileEntity entity) {
        return new Profile(
                entity.getId(),
                entity.getName(),
                entity.getLastname(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getIsActive(),
                entity.getCreatedAt() != null ? entity.getCreatedAt().toInstant() : null);
    }
}
