package com.viaja_segura.viaja_segura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/password/**").permitAll()
                        .requestMatchers("/api/users/register/passenger").permitAll()
                        .requestMatchers("/api/users/passengers").permitAll()
                        .requestMatchers("/api/users/admins").permitAll()
                        .requestMatchers("/api/users/drivers").permitAll()
                        .requestMatchers("/api/vehicles/get-all").permitAll()
                        .requestMatchers("/api/vehicles/driver/**").permitAll()
                        .requestMatchers("/api/users/admins/{id}").permitAll()
                        .requestMatchers("/api/users/passengers/{id}").permitAll()
                        .requestMatchers("/api/users/drivers/{id}").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/api/users/drivers/available").permitAll()
                        .requestMatchers("/api/users/drivers/{id}/available").permitAll()

                        // 🔐 Solo ADMIN puede registrar conductores y admins
                        .requestMatchers("/api/users/register/admin").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/register/driver").hasAuthority("ADMIN")
                        .requestMatchers("/api/vehicles/register").hasAuthority("ADMIN")

                        // 🔒 Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
