package com.neversion.api.user.application.service;

import com.neversion.api.exception.ResourceNotFoundException;
import com.neversion.api.user.domain.model.CurrentUserContextResult;
import com.neversion.api.user.domain.model.User;
import com.neversion.api.user.domain.model.enums.UserRole;
import com.neversion.api.user.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetCurrentUserContextService unit tests")
class GetCurrentUserContextServiceUT {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    private GetCurrentUserContextService service;

    private static final String EXTERNAL_ID = "supabase-vendor";
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        service = new GetCurrentUserContextService(userRepositoryPort);
    }

    @Test
    @DisplayName("get - should return user context for vendor user")
    void get_shouldReturnUserContext_whenVendorUser() {
        User user = buildUser(UserRole.VENDOR);
        when(userRepositoryPort.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(user));

        CurrentUserContextResult result = service.get(EXTERNAL_ID);

        assertThat(result.externalId()).isEqualTo(EXTERNAL_ID);
        assertThat(result.role()).isEqualTo(UserRole.VENDOR);
    }

    @Test
    @DisplayName("get - should return user context for super admin")
    void get_shouldReturnUserContext_whenSuperAdmin() {
        when(userRepositoryPort.findByExternalId(EXTERNAL_ID))
                .thenReturn(Optional.of(buildUser(UserRole.SUPER_ADMIN)));

        CurrentUserContextResult result = service.get(EXTERNAL_ID);

        assertThat(result.externalId()).isEqualTo(EXTERNAL_ID);
        assertThat(result.role()).isEqualTo(UserRole.SUPER_ADMIN);
    }

    @Test
    @DisplayName("get - should throw when internal user is missing")
    void get_shouldThrow_whenUserMissing() {
        when(userRepositoryPort.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(EXTERNAL_ID))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private User buildUser(UserRole role) {
        return User.builder()
                .id(USER_ID)
                .externalId(EXTERNAL_ID)
                .role(role)
                .build();
    }
}
