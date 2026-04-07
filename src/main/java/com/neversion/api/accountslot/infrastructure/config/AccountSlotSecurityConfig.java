package com.neversion.api.accountslot.infrastructure.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

import com.neversion.api.config.HttpSecurityCustomizer;

@Component
public class AccountSlotSecurityConfig implements HttpSecurityCustomizer {

    @Override
    public void customize(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/account-slots/**").hasRole("ADMIN"));
    }
}
