package com.neversion.panel.config;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Main security configuration.
 *
 * <ul>
 * <li>Stateless session (no cookies/sessions — pure JWT).</li>
 * <li>CSRF disabled (stateless REST API).</li>
 * <li>CORS enabled with sensible defaults.</li>
 * <li>OAuth2 Resource Server validating Supabase JWTs (HS256).</li>
 * <li>RBAC matrix as defined in SECURITY.md.</li>
 * </ul>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${supabase.jwt.secret}")
    private String supabaseJwtSecret;

    private final SupabaseJwtAuthConverter supabaseJwtAuthConverter;

    public SecurityConfig(SupabaseJwtAuthConverter supabaseJwtAuthConverter) {
        this.supabaseJwtAuthConverter = supabaseJwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // -- Stateless: no HTTP session --
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // -- CSRF disabled for stateless REST API --
                .csrf(csrf -> csrf.disable())

                // -- CORS --
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // -- Disable form login & HTTP Basic (API-only) --
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // ================================================================
                // RBAC Access Matrix (from SECURITY.md, adapted to /api/v1/ paths)
                // ================================================================
                .authorizeHttpRequests(auth -> auth

                        // ---- Swagger / OpenAPI docs (public) ----
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // ---- Products: GET is public, mutating is ADMIN ----
                        .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasRole("ADMIN")

                        // ---- Inventory: GET is public, mutating is ADMIN ----
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventory/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/inventory/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/inventory/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/inventory/**").hasRole("ADMIN")

                        // ---- Accounts: strictly admin-only ----
                        .requestMatchers("/api/v1/accounts/**").hasRole("ADMIN")

                        // ---- Reservations: create & view/edit by UUID is public, delete is ADMIN ----
                        .requestMatchers(HttpMethod.POST, "/api/v1/reservations").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/reservations/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/reservations/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/reservations/**").hasRole("ADMIN")

                        // ---- User Guests: create & view/edit by UUID is public, delete is ADMIN ----
                        .requestMatchers(HttpMethod.POST, "/api/v1/user-guests").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/user-guests/{id}").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/v1/user-guests/{id}").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/user-guests/**").hasRole("ADMIN")

                        // ---- Everything else requires authentication ----
                        .anyRequest().authenticated())

                // -- OAuth2 Resource Server: validate JWTs with our custom converter --
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(jwtDecoder())
                                .jwtAuthenticationConverter(supabaseJwtAuthConverter)));

        return http.build();
    }

    /**
     * Decodes Supabase JWTs using the project's HS256 secret key.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        byte[] secretBytes = supabaseJwtSecret.getBytes();
        SecretKey secretKey = new SecretKeySpec(secretBytes, "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(secretKey).build();
    }

    /**
     * CORS configuration. Allows all origins during development.
     * Should be restricted in production.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
