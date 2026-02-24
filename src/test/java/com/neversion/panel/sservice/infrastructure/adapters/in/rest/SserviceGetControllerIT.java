package com.neversion.panel.sservice.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.neversion.panel.exception.ResourceNotFoundException;
import com.neversion.panel.sservice.application.port.in.GetSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.dto.SserviceResponse;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper.SserviceMapper;

@WebMvcTest(SserviceGetController.class)
class SserviceGetControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetSserviceUseCase getSserviceUseCase;

    @MockitoBean
    private SserviceMapper sserviceMapper;

    private Sservice buildSservice(Integer id, String name) {
        return Sservice.builder()
                .id(id)
                .name(name)
                .description("Test description")
                .category(CategoryType.PLATAFORMA)
                .build();
    }

    private SserviceResponse buildResponse(Integer id, String name) {
        return new SserviceResponse(id, name, "Test description", CategoryType.PLATAFORMA, List.of());
    }

    // -- GET by ID --

    @Nested
    @DisplayName("GET /api/v1/sservices/{id}")
    class GetById {

        @Test
        @DisplayName("→ 200 when service exists")
        void getById_shouldReturn200() throws Exception {
            Sservice sservice = buildSservice(1, "Netflix");
            SserviceResponse response = buildResponse(1, "Netflix");

            when(getSserviceUseCase.getById(1)).thenReturn(sservice);
            when(sserviceMapper.toResponse(sservice)).thenReturn(response);

            mockMvc.perform(get("/api/v1/sservices/{id}", 1))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Netflix"));
        }

        @Test
        @DisplayName("→ 404 when service does not exist")
        void getById_shouldReturn404_whenNotExists() throws Exception {
            when(getSserviceUseCase.getById(anyInt()))
                    .thenThrow(new ResourceNotFoundException("Sservice with id 999 not found"));

            mockMvc.perform(get("/api/v1/sservices/{id}", 999))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message").value("Sservice with id 999 not found"));
        }
    }

    // -- GET by name --

    @Nested
    @DisplayName("GET /api/v1/sservices?name=")
    class GetByName {

        @Test
        @DisplayName("→ 200 when service exists by name")
        void getByName_shouldReturn200() throws Exception {
            Sservice sservice = buildSservice(1, "Netflix");
            SserviceResponse response = buildResponse(1, "Netflix");

            when(getSserviceUseCase.getByName("Netflix")).thenReturn(sservice);
            when(sserviceMapper.toResponse(sservice)).thenReturn(response);

            mockMvc.perform(get("/api/v1/sservices").param("name", "Netflix"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Netflix"));
        }

        @Test
        @DisplayName("→ 404 when service does not exist by name")
        void getByName_shouldReturn404_whenNotExists() throws Exception {
            when(getSserviceUseCase.getByName(anyString()))
                    .thenThrow(new ResourceNotFoundException("Sservice with name Unknown not found"));

            mockMvc.perform(get("/api/v1/sservices").param("name", "Unknown"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404));
        }
    }

    // -- GET all --

    @Nested
    @DisplayName("GET /api/v1/sservices")
    class GetAll {

        @Test
        @DisplayName("→ 200 with list of services")
        void getAll_shouldReturn200_withList() throws Exception {
            Sservice s1 = buildSservice(1, "Netflix");
            Sservice s2 = buildSservice(2, "Spotify");
            SserviceResponse r1 = buildResponse(1, "Netflix");
            SserviceResponse r2 = buildResponse(2, "Spotify");

            when(getSserviceUseCase.getAll()).thenReturn(List.of(s1, s2));
            when(sserviceMapper.toResponse(s1)).thenReturn(r1);
            when(sserviceMapper.toResponse(s2)).thenReturn(r2);

            mockMvc.perform(get("/api/v1/sservices"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].name").value("Netflix"))
                    .andExpect(jsonPath("$[1].name").value("Spotify"));
        }

        @Test
        @DisplayName("→ 200 with empty list when no services exist")
        void getAll_shouldReturn200_withEmptyList() throws Exception {
            when(getSserviceUseCase.getAll()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/v1/sservices"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));
        }
    }
}
