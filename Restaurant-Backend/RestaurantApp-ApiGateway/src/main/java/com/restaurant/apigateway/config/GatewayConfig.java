package com.restaurant.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.restaurant.apigateway.filter.AuthFilter;

@Configuration
public class GatewayConfig {

	@Autowired
    AuthFilter authFilter;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("UserService",r -> r.path("/api/v1/userservice/verify/**")
                		.filters(f -> f.filters(authFilter))
                		.uri("https://ra-us-prod.up.railway.app"))
                .route("UserService",r -> r.path("/api/v1/userservice/**")
                		.uri("https://ra-us-prod.up.railway.app"))
                .route("ProductService",r -> r.path("/api/v1/productservice/verify/**")
                        .filters(f-> f.filters(authFilter))
                        .uri("https://ra-ps-prod.up.railway.app"))
                .route("ProductService",r -> r.path("/api/v1/productservice/**")
                        .uri("https://ra-ps-prod.up.railway.app"))
                .route("FeedbackService",r -> r.path("/api/v1/feedbackservice/**")
                		.uri("https://ra-fs-prod.up.railway.app"))
                .route("MessagingService",r -> r.path("/api/v1/messagingservice/**")
                		.uri("https://ra-ms-prod.up.railway.app"))
                .route("AuthenticationService",r -> r.path("/api/v1/authservice/**")
                		.uri("https://ra-as-prod.up.railway.app"))
                .build();
    }

	/*
	 * @Bean public RestTemplate getRestTemplate() { return new RestTemplate(); }
	 * 
	 * @Bean public WebFilter responseFilter(){ return new PostGlobalFilter(); }
	 */
}
