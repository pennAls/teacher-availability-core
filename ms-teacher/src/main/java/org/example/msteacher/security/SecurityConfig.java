package org.example.msteacher.security;

import jakarta.ws.rs.HttpMethod;
import org.example.mssecurity.security.CustomSecurityExceptionHandler;
import org.example.mssecurity.security.SecurityFilter;
import org.hibernate.mapping.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;
    private final CustomSecurityExceptionHandler customSecurityExceptionHandler;

    public SecurityConfig(SecurityFilter securityFilter, CustomSecurityExceptionHandler customSecurityExceptionHandler) {
        this.securityFilter = securityFilter;
        this.customSecurityExceptionHandler = customSecurityExceptionHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customSecurityExceptionHandler)
                        .accessDeniedHandler(customSecurityExceptionHandler)
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()

                        .requestMatchers(HttpMethod.GET, "/users/getbyId").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users/getAll").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/users/{id}/status").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/schools/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/schools/{id}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/schools/getAll").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/schools/{id}/status").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/disciplines/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/disciplines/school/{schoolId}").permitAll()
                        .requestMatchers(HttpMethod.GET, "/disciplines/getAll").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/disciplines/{id}/status").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/teachers/create").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/teachers/me").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/teachers/getAll").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/academic-degrees/create").hasRole("TEACHER")

                        .requestMatchers(HttpMethod.POST, "/interests/create").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/interests/me").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/interests/{id}").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/interests/admin/report").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/availabilities/create").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/availabilities/me").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.DELETE, "/availabilities/delete/{id}").hasRole("TEACHER")
                        .requestMatchers(HttpMethod.GET, "/availabilities/admin/report").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}