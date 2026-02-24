package com.neversion.panel.sservice.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sservice.domain.port.out.SserviceRepositoryPort;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetSserviceService Unit Tests")
class GetSserviceServiceTest {

    @Mock
    private SserviceRepositoryPort sserviceRepositoryPort;

    private GetSserviceService getSserviceService;

    @BeforeEach
    void setUp() {
        getSserviceService = new GetSserviceService(sserviceRepositoryPort);
    }

    @Nested
    @DisplayName("getById")
    class GetById {

        @Test
        @DisplayName("should return Sservice when it exists")
        void shouldReturnSservice_whenExists() {
            // Given
            Integer id = 1;
            Sservice expected = Sservice.builder()
                    .id(id)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.PLATAFORMA)
                    .build();

            when(sserviceRepositoryPort.findById(id)).thenReturn(Optional.of(expected));

            // When
            Sservice result = getSserviceService.getById(id);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getId()).isEqualTo(id);
            assertThat(result.getName()).isEqualTo("Netflix");
            assertThat(result.getCategory()).isEqualTo(CategoryType.PLATAFORMA);
            verify(sserviceRepositoryPort).findById(id);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when Sservice does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            // Given
            Integer id = 999;
            when(sserviceRepositoryPort.findById(id)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getSserviceService.getById(id))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(String.valueOf(id));
        }
    }

    @Nested
    @DisplayName("getByName")
    class GetByName {

        @Test
        @DisplayName("should return Sservice when name exists")
        void shouldReturnSservice_whenExists() {
            // Given
            String name = "Spotify";
            Sservice expected = Sservice.builder()
                    .id(2)
                    .name(name)
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.SUSCRIPCION)
                    .build();

            when(sserviceRepositoryPort.findByName(name)).thenReturn(Optional.of(expected));

            // When
            Sservice result = getSserviceService.getByName(name);

            // Then
            assertThat(result).isNotNull();
            assertThat(result.getName()).isEqualTo(name);
            assertThat(result.getCategory()).isEqualTo(CategoryType.SUSCRIPCION);
            verify(sserviceRepositoryPort).findByName(name);
        }

        @Test
        @DisplayName("should throw ResourceNotFoundException when name does not exist")
        void shouldThrowResourceNotFoundException_whenNotExists() {
            // Given
            String name = "NonExistent";
            when(sserviceRepositoryPort.findByName(name)).thenReturn(Optional.empty());

            // When / Then
            assertThatThrownBy(() -> getSserviceService.getByName(name))
                    .isInstanceOf(ResourceNotFoundException.class)
                    .hasMessageContaining(name);
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("should return list of Sservices when they exist")
        void shouldReturnListOfSservices() {
            // Given
            Sservice netflix = Sservice.builder()
                    .id(1)
                    .name("Netflix")
                    .description("Streaming platform")
                    .imageUrl("https://img.example.com/netflix.png")
                    .category(CategoryType.PLATAFORMA)
                    .build();

            Sservice spotify = Sservice.builder()
                    .id(2)
                    .name("Spotify")
                    .description("Music streaming")
                    .imageUrl("https://img.example.com/spotify.png")
                    .category(CategoryType.SUSCRIPCION)
                    .build();

            when(sserviceRepositoryPort.findAll()).thenReturn(List.of(netflix, spotify));

            // When
            List<Sservice> result = getSserviceService.getAll();

            // Then
            assertThat(result)
                    .isNotNull()
                    .hasSize(2)
                    .extracting(Sservice::getName)
                    .containsExactly("Netflix", "Spotify");
            verify(sserviceRepositoryPort).findAll();
        }

        @Test
        @DisplayName("should return empty list when no Sservices exist")
        void shouldReturnEmptyList_whenNoSservicesExist() {
            // Given
            when(sserviceRepositoryPort.findAll()).thenReturn(Collections.emptyList());

            // When
            List<Sservice> result = getSserviceService.getAll();

            // Then
            assertThat(result)
                    .isNotNull()
                    .isEmpty();
            verify(sserviceRepositoryPort).findAll();
        }
    }
}
