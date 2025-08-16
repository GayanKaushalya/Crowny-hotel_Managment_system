package com.crownydev.CrownyHotel.security;

import com.crownydev.CrownyHotel.service.CustomUserDetailsService;
import io.swagger.v3.oas.models.OpenAPI; // <-- ADD THIS IMPORT
import io.swagger.v3.oas.models.info.Info;   // <-- ADD THIS IMPORT
import io.swagger.v3.oas.models.info.License; // <-- ADD THIS IMPORT
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWTAuthFilter jwtAuthFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests(auth -> auth

                // 1. PUBLIC Endpoints (No login required)
                .requestMatchers(
                        "/auth/**",
                        "/files/**",
                        "/rooms/all",
                        "/rooms/types",
                        "/rooms/available-rooms-by-date-and-type",
                        "/bookings/confirmation/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html"

                ).permitAll()

                // 2. ADMIN-ONLY Endpoints (Specific admin actions)
                .requestMatchers(
                        "/users/all",
                        "/users/delete/**",
                        "/bookings/all",
                        "/rooms/add",
                        "/rooms/update/**",
                        "/rooms/delete/**"
                ).hasAuthority("ADMIN")

                // 3. AUTHENTICATED User Endpoints (Any logged-in user, USER or ADMIN)
                .requestMatchers(
                        "/users/my-info",
                        "/users/update/**",
                        "/bookings/user/**",
                        "/bookings/book-room/**",
                        "/bookings/cancel/**",
                        "/rooms/{roomId}"
                ).authenticated()

                // 4. CATCH-ALL: Any other request not listed above requires authentication.
                .anyRequest().authenticated()
        );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CrownyHotel API")
                        .version("1.0")
                        .description("This is the API documentation for the CrownyHotel backend services. It provides endpoints for managing users, rooms, and bookings.")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

}