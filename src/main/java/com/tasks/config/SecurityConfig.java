package com.tasks.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tasks.auth.JwtAuthenticationFilter;
import com.tasks.constant.ErrorCode;
import com.tasks.exception.ErrorResponse;

import tools.jackson.databind.ObjectMapper;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
            List.of("http://localhost:4200", "https://angular-tasks-8npt.onrender.com")
        );

        configuration.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
            List.of("*")
        );

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
            new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**", "/users").permitAll()
                .anyRequest().authenticated()
            ).exceptionHandling(exception -> exception
            	    .accessDeniedHandler((request, response, ex) -> {
            	        ErrorCode error = ErrorCode.ACCESS_FORBIDDEN;
            	        response.setStatus(error.getStatus().value());
            	        response.setContentType("application/json");

            	        response.getWriter().write(
            	            new ObjectMapper().writeValueAsString(
            	                new ErrorResponse(
            	                    error.getCode(),
            	                    error.getMessage()
            	                )
            	            )
            	        );
            	    })
            	    .authenticationEntryPoint((request, response, ex) -> {

            	        ErrorCode error = ErrorCode.UNAUTHORIZED;

            	        response.setStatus(error.getStatus().value());
            	        response.setContentType("application/json");

            	        response.getWriter().write(
            	            new ObjectMapper().writeValueAsString(
            	                new ErrorResponse(
            	                    error.getCode(),
            	                    error.getMessage()
            	                )
            	            )
            	        );
            	    })
            	)
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }
}