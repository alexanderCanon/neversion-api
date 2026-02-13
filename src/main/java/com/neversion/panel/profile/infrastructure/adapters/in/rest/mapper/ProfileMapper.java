package com.neversion.panel.profile.infrastructure.adapters.in.rest.mapper;

import org.springframework.stereotype.Component;

import com.neversion.panel.profile.domain.model.Profile;
import com.neversion.panel.profile.infrastructure.adapters.in.rest.dto.ProfileResponse;

@Component
public class ProfileMapper {

    public ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(
            profile.name(),
            profile.lastname(),
            profile.email(),
            profile.phone(),
            profile.isActive()
        );
    }
}
