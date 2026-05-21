package com.k24.hospital.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/login",
                                "/register",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .requestMatchers("/doctor/**")
                        .hasRole("DOCTOR")

                        .requestMatchers("/patient/**")
                        .hasRole("PATIENT")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(login -> login

                        .loginPage("/login")

                        .defaultSuccessUrl(
                                "/redirect-by-role",
                                true
                        )

                        .permitAll()
                )

                .logout(logout -> logout

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                );

        return http.build();
    }
}