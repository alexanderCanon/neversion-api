package com.neversion.panel.userguest.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.userguest.domain.model.UserGuest;
import com.neversion.panel.userguest.domain.port.out.UserGuestRepositoryPort;

/**
 * Unit tests for UserGuest services:
 * CreateUserGuestService, GetUserGuestService, DeactivateUserGuestService.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserGuest services unit tests")
class UserGuestServicesUT {

    @Mock
    private UserGuestRepositoryPort userGuestRepositoryPort;

    private CreateUserGuestService createUserGuestService;
    private GetUserGuestService getUserGuestService;
    private DeactivateUserGuestService deactivateUserGuestService;

    private static final UUID GUEST_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        createUserGuestService = new CreateUserGuestService(userGuestRepositoryPort);
        getUserGuestService = new GetUserGuestService(userGuestRepositoryPort);
        deactivateUserGuestService = new DeactivateUserGuestService(userGuestRepositoryPort);
    }

    private UserGuest buildGuest() {
        return UserGuest.builder()
                .id(GUEST_ID)
                .name("Juan Pérez")
                .email("juan@gmail.com")
                .phone("55551234")
                .build();
    }

    // ── CreateUserGuestService ────────────────────────────────────────

    @Nested
    @DisplayName("CreateUserGuestService")
    class Create {

        @Test
        @DisplayName("create - should delegate save to repository")
        void create_shouldDelegateSaveToRepository() {
            // Given
            UserGuest guest = buildGuest();
            when(userGuestRepositoryPort.save(guest)).thenReturn(guest);

            // When
            UserGuest result = createUserGuestService.create(guest);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo("Juan Pérez");
            verify(userGuestRepositoryPort).save(guest);
        }
    }

    // ── GetUserGuestService ───────────────────────────────────────────

    @Nested
    @DisplayName("GetUserGuestService")
    class Get {

        @Test
        @DisplayName("getById - should return guest when found")
        void getById_shouldReturnGuest_whenFound() {
            // Given
            when(userGuestRepositoryPort.findById(GUEST_ID)).thenReturn(Optional.of(buildGuest()));

            // When
            UserGuest result = getUserGuestService.getById(GUEST_ID);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(GUEST_ID);
        }

        @Test
        @DisplayName("getById - should throw ResourceNotFoundException when not found")
        void getById_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(userGuestRepositoryPort.findById(GUEST_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getUserGuestService.getById(GUEST_ID))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(GUEST_ID.toString());
        }

        @Test
        @DisplayName("getAll - should return all guests")
        void getAll_shouldReturnAllGuests() {
            // Given
            when(userGuestRepositoryPort.findAll()).thenReturn(List.of(buildGuest()));

            // When
            List<UserGuest> result = getUserGuestService.getAll();

            // Then
            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("getByName - should delegate to repository")
        void getByName_shouldDelegateToRepository() {
            // Given
            when(userGuestRepositoryPort.findByName("Juan")).thenReturn(List.of(buildGuest()));

            // When
            List<UserGuest> result = getUserGuestService.getByName("Juan");

            // Then
            assertThat(result).hasSize(1);
        }

        @Test
        @DisplayName("getByPhone - should delegate to repository")
        void getByPhone_shouldDelegateToRepository() {
            // Given
            when(userGuestRepositoryPort.findByPhone("55551234")).thenReturn(List.of(buildGuest()));

            // When
            List<UserGuest> result = getUserGuestService.getByPhone("55551234");

            // Then
            assertThat(result).hasSize(1);
        }
    }

    // ── DeactivateUserGuestService ────────────────────────────────────

    @Nested
    @DisplayName("DeactivateUserGuestService")
    class Deactivate {

        @Test
        @DisplayName("deactivate - should deactivate when guest exists")
        void deactivate_shouldDeactivate_whenExists() {
            // Given
            when(userGuestRepositoryPort.findById(GUEST_ID)).thenReturn(Optional.of(buildGuest()));

            // When
            deactivateUserGuestService.deactivate(GUEST_ID);

            // Then
            verify(userGuestRepositoryPort).deactivate(GUEST_ID);
        }

        @Test
        @DisplayName("deactivate - should throw ResourceNotFoundException when not found")
        void deactivate_shouldThrowResourceNotFound_whenNotFound() {
            // Given
            when(userGuestRepositoryPort.findById(GUEST_ID)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> deactivateUserGuestService.deactivate(GUEST_ID))
                    .isInstanceOf(ResourceNotFoundException.class);
        }
    }
}
