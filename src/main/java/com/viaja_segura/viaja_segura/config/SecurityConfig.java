package com.viaja_segura.viaja_segura.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 Endpoints públicos
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/users/register/passenger").permitAll()
                        .requestMatchers("/api/users/passengers").permitAll()
                        .requestMatchers("/api/users/admins").permitAll()
                        .requestMatchers("/api/users/drivers").permitAll()

                        // 🔐 Solo ADMIN puede registrar conductores y admins
                        .requestMatchers("/api/users/register/admin").hasAuthority("ADMIN")
                        .requestMatchers("/api/users/register/driver").hasAuthority("ADMIN")

                        // 🔒 Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
