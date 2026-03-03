package com.neversion.panel.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI panelOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Neversion Panel API")
                        .description("Admin panel API for product management, inventory, accounts, and reservations")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Neversion")
                                .email("support@neversion.com")));
    }
}
