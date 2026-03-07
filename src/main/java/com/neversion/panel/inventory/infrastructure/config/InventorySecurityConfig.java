package com.neversion.panel.inventory.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.neversion.panel.config.HttpSecurityCustomizer;

/**
 * Inventory: GET is public (catalog pricing), mutating is ADMIN-only.
 */
@Configuration
public class InventorySecurityConfig implements HttpSecurityCustomizer {

    @Override
    public void customize(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/v1/inventory/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/inventory/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/inventory/**").hasRole("ADMIN"));
    }
}
