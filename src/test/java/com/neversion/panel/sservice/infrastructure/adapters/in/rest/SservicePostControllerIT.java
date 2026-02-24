package com.neversion.panel.sservice.infrastructure.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neversion.panel.config.SecurityConfig;
import com.neversion.panel.sservice.application.port.in.CreateSserviceUseCase;
import com.neversion.panel.sservice.domain.model.Sservice;
import com.neversion.panel.sservice.domain.model.enums.CategoryType;
import com.neversion.panel.sservice.infrastructure.adapters.in.rest.mapper.SserviceMapper;

@WebMvcTest(SservicePostController.class)
@Import(SecurityConfig.class)
class SservicePostControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CreateSserviceUseCase createSserviceUseCase;

    @MockitoBean
    private SserviceMapper sserviceMapper;

    // -- Happy path --

    @Test
    @DisplayName("POST /api/v1/sservices → 201 CREATED when request is valid")
    void createSservice_shouldReturn201() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "Netflix",
                    "description": "Streaming service",
                    "imageUrl": "https://img.com/netflix.png",
                    "category": "PLATAFORMA",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "duration": "1 month",
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        Sservice domainInput = Sservice.builder()
                .name("Netflix")
                .description("Streaming service")
                .imageUrl("https://img.com/netflix.png")
                .category(CategoryType.PLATAFORMA)
                .build();

        Sservice domainOutput = Sservice.builder()
                .id(1)
                .name("Netflix")
                .description("Streaming service")
                .imageUrl("https://img.com/netflix.png")
                .category(CategoryType.PLATAFORMA)
                .build();

        when(sserviceMapper.toDomain(any())).thenReturn(domainInput);
        when(createSserviceUseCase.create(any(Sservice.class))).thenReturn(domainOutput);

        // When & Then
        mockMvc.perform(post("/api/v1/sservices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Netflix"));
    }

    // -- Validation tests --

    @Test
    @DisplayName("POST /api/v1/sservices → 400 when name is blank")
    void createSservice_shouldReturn400_whenNameIsBlank() throws Exception {
        String requestBody = """
                {
                    "name": "",
                    "description": "Streaming service",
                    "category": "PLATAFORMA",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "duration": "1 month",
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/v1/sservices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/sservices → 400 when name is too short")
    void createSservice_shouldReturn400_whenNameIsTooShort() throws Exception {
        String requestBody = """
                {
                    "name": "ab",
                    "description": "Streaming service",
                    "category": "PLATAFORMA",
                    "items": [
                        {
                            "priceAmount": 9.99,
                            "duration": "1 month",
                            "accountType": "individual"
                        }
                    ]
                }
                """;

        mockMvc.perform(post("/api/v1/sservices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/sservices → 400 when items list is empty")
    void createSservice_shouldReturn400_whenItemsAreEmpty() throws Exception {
        String requestBody = """
                {
                    "name": "Netflix",
                    "description": "Streaming service",
                    "category": "PLATAFORMA",
                    "items": []
                }
                """;

        mockMvc.perform(post("/api/v1/sservices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
