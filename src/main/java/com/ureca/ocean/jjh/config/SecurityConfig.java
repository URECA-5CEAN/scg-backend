package com.ureca.ocean.jjh.config;



import com.ureca.ocean.jjh.jwt.JwtAuthenticationWebFilter;
import com.ureca.ocean.jjh.jwt.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;


import lombok.RequiredArgsConstructor;
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
                                "/swagger-ui/**",       // swagger-ui.html, css, js 등 정적 리소스 포함
                                "/v3/api-docs/**",      // OpenAPI json 문서
                                "/swagger-resources/**",// Swagger 관련 리소스
                                "/webjars/**",          // Swagger UI 의존성 리소스
                                "/api/auth/login",
                                "/api/user/signup"
                        ).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(new JwtAuthenticationWebFilter(jwtUtil),  SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
}
