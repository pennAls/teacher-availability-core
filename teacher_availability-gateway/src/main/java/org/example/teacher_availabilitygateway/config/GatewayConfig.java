package org.example.teacher_availabilitygateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;
import static org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> securityRoute() {
        return route("ms-security")
                .route(path("/auth/**", "/users/**"), http())
                .filter(lb("MS-SECURITY"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> academicRoute() {
        return route("ms-academic")
                .route(path("/ies/**", "/schools/**", "/disciplines/**"), http())
                .filter(lb("MS-ACADEMIC"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> teacherRoute() {
        return route("ms-teacher")
                .route(path("/teachers/**", "/degrees/**"), http())
                .filter(lb("MS-TEACHER"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> planningRoute() {
        return route("ms-planning")
                .route(path("/availabilities/**", "/interests/**"), http())
                .filter(lb("MS-PLANNING"))
                .build();
    }
}