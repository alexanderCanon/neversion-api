package com.neversion.panel.sservice.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sservice.domain.port.out.SserviceRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateSserviceService Unit Tests")
class CreateSserviceServiceTest {

    @Mock
    private SserviceRepositoryPort sserviceRepositoryPort;

    private CreateSserviceService createSserviceService;

    @BeforeEach
    void setUp() {
        createSserviceService = new CreateSserviceService(sserviceRepositoryPort);
    }

    @Test
    @DisplayName("create - should return the saved Sservice with generated id")
    void create_shouldReturnSavedSservice() {
        // Given
        Sservice input = Sservice.builder()
                .name("Netflix")
                .description("Streaming platform")
                .imageUrl("https://img.example.com/netflix.png")
                .category(CategoryType.PLATAFORMA)
                .build();

        Sservice persisted = Sservice.builder()
                .id(1)
                .name("Netflix")
                .description("Streaming platform")
                .imageUrl("https://img.example.com/netflix.png")
                .category(CategoryType.PLATAFORMA)
                .build();

        when(sserviceRepositoryPort.save(input)).thenReturn(persisted);

        // When
        Sservice result = createSserviceService.create(input);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Netflix");
        assertThat(result.getDescription()).isEqualTo("Streaming platform");
        assertThat(result.getCategory()).isEqualTo(CategoryType.PLATAFORMA);
    }

    @Test
    @DisplayName("create - should delegate to repository port exactly once")
    void create_shouldDelegateToRepositoryPort() {
        // Given
        Sservice input = Sservice.builder()
                .name("Spotify")
                .description("Music streaming")
                .imageUrl("https://img.example.com/spotify.png")
                .category(CategoryType.SUSCRIPCION)
                .build();

        when(sserviceRepositoryPort.save(input)).thenReturn(input);

        // When
        createSserviceService.create(input);

        // Then
        verify(sserviceRepositoryPort, times(1)).save(input);
        verifyNoMoreInteractions(sserviceRepositoryPort);
    }
}
