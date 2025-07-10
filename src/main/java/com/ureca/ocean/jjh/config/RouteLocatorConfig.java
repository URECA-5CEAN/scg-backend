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
                .route("path_route", r-> r.path("/api/auth/**")
                        .uri("http://10.0.1.17:8081"))
//                .route("path_route", r-> r.path("/api/auth/**")
//                        .uri("http://localhost:8081"))
                .route("path_route", r-> r.path("/api/map/**")
                        .uri("http://10.0.1.239:8082"))
                .route("path_route", r-> r.path("/api/ai/**")
                        .uri("http://10.0.1.141:8082"))
                .route("path_route", r -> r.path("/api/user/**")
                        .uri("http://10.0.2.41:8081"))
//                .route("path_route", r -> r.path("/api/user/**")
//                        .uri("http://localhost:8082"))
//                .route("host_route", r -> r.host("*.myhost.org")
//                        .uri("http://httpbin.org"))
//                .route("rewrite_route", r -> r.host("*.rewrite.org")
//                        .filters(f -> f.rewritePath("/foo/(?<segment>.*)", "/${segment}"))
//                        .uri("http://httpbin.org"))
//                .route("hystrix_route", r -> r.host("*.hystrix.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd")))
//                        .uri("http://httpbin.org"))
//                .route("hystrix_fallback_route", r -> r.host("*.hystrixfallback.org")
//                        .filters(f -> f.hystrix(c -> c.setName("slowcmd").setFallbackUri("forward:/hystrixfallback")))
//                        .uri("http://httpbin.org"))
//                .route("limit_route", r -> r
//                        .host("*.limited.org").and().path("/anything/**")
//                        .filters(f -> f.requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter())))
//                        .uri("http://httpbin.org"))
                .build();
    }
}
