package com.ureca.ocean.jjh.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RouteLocatorConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("websocket_route", r -> r
                        .path("/ws/chat")
                        .uri("ws://10.0.2.41:8082")) // WebSocket 서버 주소

                //eureka 적용 전 라우팅
//                .route("auth_route", r -> r.path("/api/auth/**")
//                        .uri("http://10.0.1.17:8081"))
//                .route("map_route", r -> r.path("/api/map/**")
//                        .uri("http://10.0.1.239:8082"))
//                .route("ai_route", r -> r.path("/api/ai/**")
//                        .uri("http://10.0.1.141:8082"))
//                .route("user_route", r -> r.path("/api/user/**")
//                        .uri("http://10.0.2.41:8082"))

                //eureka 적용 후 라우팅
                .route("auth_route", r -> r.path("/api/auth/**")
                        .uri("lb://auth-backend"))
                .route("map_route", r -> r.path("/api/map/**")
                        .uri("lb://map-backend"))
                .route("ai_route", r -> r.path("/api/ai/**")
                        .uri("lb://ai-backend"))
                .route("user_route", r -> r.path("/api/user/**")
                        .uri("lb://user-backend"))

                .build();
    }
}
