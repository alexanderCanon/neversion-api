package com.neversion.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("OpenAPI Specification Export Test")
class OpenApiExportTest extends BaseWebIntegrationTest {

    @Test
    @DisplayName("export OpenAPI 3.0 specification to target/openapi.json")
    void exportOpenApiSpec() throws Exception {
        MvcResult result = mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andReturn();

        String swaggerJson = result.getResponse().getContentAsString();
        Path targetDir = Paths.get("target");
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        Files.writeString(targetDir.resolve("openapi.json"), swaggerJson);
    }
}
