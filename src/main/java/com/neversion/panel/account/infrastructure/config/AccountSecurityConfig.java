package com.neversion.panel.account.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.neversion.panel.config.HttpSecurityCustomizer;

/**
 * Accounts: strictly admin-only for all operations.
 */
@Configuration
public class AccountSecurityConfig implements HttpSecurityCustomizer {

    @Override
    public void customize(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/accounts/**").hasRole("ADMIN"));
    }
}
