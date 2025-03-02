package com.emmanuel.microservices.api_gateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

//This class is used to configure the API Gateway routes
@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {

        Function<PredicateSpec, Buildable<Route>> routeFunction = p -> p.path("/get") //This method is used to define the path for the route.
                .filters(f -> f
                        .addRequestHeader("MyHeader", "MyURI") //Before rourting the request to the new url, we may add other authentification parameters. This method is used to add a request header to the route.
                        .addRequestParameter("Param", "MyValue")) //This method is used to add a request parameter to the route.
                .uri("http://httpbin.org:80/get");// /get would be redirected to http://httpbin.org:80/get

        return builder.routes()
                .route(routeFunction)
                .route(r -> r.path("/currency-exchange/**") //Alternatively to creating the routeFunction variable, we can implement the same action inline.
                        .uri("lb://currency-exchange")) //This method is used to define the URI for the route. lb:// is used to specify that the request should be load balanced.
                .route(r -> r.path("/currency-conversion/**")
                        .uri("lb://currency-conversion"))// the path /currency-conversion/** is redirected to the currency-conversion service
                .route(r -> r.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion"))
                .route(r -> r.path("/currency-conversion-new/**")
                        .filters(f -> f.rewritePath("/currency-conversion-new/(?<segment>.*)", "/currency-conversion-feign/${segment}"))//This method is used to rewrite the path of the request. In this case, the path /currency-conversion-new/** is rewritten to /currency-conversion-feign/**
                        .uri("lb://currency-conversion"))//after rewriting the path, the request is redirected to the currency-conversion service
                .build();
    }
}
