package com.ureca.ocean.jjh.config;

import com.ureca.ocean.jjh.jwt.JwtAuthenticationWebFilter;
import com.ureca.ocean.jjh.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final MyAuthenticationEntryPoint entryPoint;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(
                                "/",
                                "/swagger-ui/index.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/api-docs/swagger-config",
                                "/api-ui.html",
                                "/webjars/**",
                                "/api/auth/login",
                                "/api/user/signup",
                                "/api/auth/v3/api-docs",
                                "/api/user/v3/api-docs",
                                "/api/ai/v3/api-docs",
                                "/api/map/v3/api-docs",
                                "/api/store",
                                "/api/map/store",
                                "/api/map/store/*"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtAuthenticationWebFilter(jwtUtil), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
