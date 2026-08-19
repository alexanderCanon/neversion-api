package com.neversion.api.account.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import com.neversion.api.config.HttpSecurityCustomizer;

/**
 * Accounts: vendor manages their own accounts, super_admin has full access.
 */
@Configuration
public class AccountSecurityConfig implements HttpSecurityCustomizer {

    @Override
    public void customize(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/accounts/**")
                .hasAnyRole("VENDOR", "SUPER_ADMIN"));
    }
}
