package com.learning.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p ->
                        p.path("/learning/accountsms/**")
                                .filters(f ->
                                        f.rewritePath("/learning/accountsms/(?<segment>.*)", "/${segment}")
                                                .addResponseHeader("X-RESPONSE-TIME", LocalDateTime.now().toString()))
                                .uri("lb://ACCOUNTS"))
                .route(p ->
                        p.path("/learning/cardsms/**")
                                .filters(f -> f.rewritePath("/learning/cardsms/(?<segment>.*)", "/${segment}"))
                                .uri("lb://CARDS"))
                .route(p ->
                        p.path("/learning/loansms/**")
                                .filters(f -> f.rewritePath("/learning/loansms/(?<segment>.*)", "/${segment}"))
                                .uri("lb://LOANS"))
                .build();
    }

}
