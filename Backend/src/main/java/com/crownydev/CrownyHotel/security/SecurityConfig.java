package com.crownydev.CrownyHotel.security;

import com.crownydev.CrownyHotel.service.CustomUserDetailsService;
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

/**
 * This is the main security configuration class for the application.
 * It brings together all the security components and defines the rules.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // This enables method-level security checks like @PreAuthorize
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JWTAuthFilter jwtAuthFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, JWTAuthFilter jwtAuthFilter) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    /**
     * The SecurityFilterChain is the core of Spring Security's configuration.
     * It defines the security rules for all HTTP requests.
     */
    // Inside your SecurityConfig.java file

    // In your SecurityConfig.java file

    // In your SecurityConfig.java file

    // In your SecurityConfig.java file

    // PASTE THIS ENTIRE METHOD INTO YOUR SecurityConfig.java, REPLACING THE OLD ONE

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
                        "/bookings/confirmation/**"
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
                //    This new rule fixes the error by allowing users to see room details.
                .requestMatchers(
                        "/users/my-info",
                        "/users/update/**",
                        "/bookings/user/**",
                        "/bookings/book-room/**",
                        "/bookings/cancel/**",
                        "/rooms/{roomId}" // <-- FIX: Allows access to GET /rooms/2, etc.
                ).authenticated()

                // 4. CATCH-ALL: Any other request not listed above requires authentication.
                .anyRequest().authenticated()
        );

        return http.build();
    }

    /**
     * This bean defines our CORS (Cross-Origin Resource Sharing) policy.
     */
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

    /**
     * This bean defines the custom Authentication Provider.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    /**
     * This bean defines the password hashing algorithm.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This bean exposes the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}