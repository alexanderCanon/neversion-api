package com.neversion.api.user.domain.model;

import com.neversion.api.user.domain.model.enums.UserRole;

/**
 * Authenticated platform user context resolved from the Supabase JWT subject.
 */
public record CurrentUserContextResult(
        String externalId,
        UserRole role
) {
}
