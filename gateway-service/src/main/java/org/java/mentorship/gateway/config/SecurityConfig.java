package org.java.mentorship.gateway.config;

import lombok.RequiredArgsConstructor;
import org.java.mentorship.contracts.user.client.SessionFeignClient;
import org.java.mentorship.contracts.user.client.UserFeignClient;
import org.java.mentorship.gateway.security.filter.SecurityFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserFeignClient userFeignClient;
    private final SessionFeignClient sessionFeignClient;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // No HTTP sessions, stateless API
                .addFilterBefore(new SecurityFilter(sessionFeignClient, userFeignClient),
                        UsernamePasswordAuthenticationFilter.class)  // Add your custom filter
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.PUT, "/users/verify/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/recovery/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/recovery/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/sessions").permitAll()
                        .anyRequest().authenticated());  // Other endpoints require authentication

        return http.build();
    }

}
