package com.emmanuel.microservices.api_gateway;
// In addition to ApiGatewayConfiguration class, we can configure global filters using this class.

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private Logger logger = LoggerFactory.getLogger(LoggingFilter.class); //This is used to log messages

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) { //This method is used to filter the request and response
        logger.info("Path of the request received -> {}", exchange.getRequest().getPath()); //This is used to log the path of the request e.g /currency-exchange/from/USD/to/INR
        return chain.filter(exchange); //This is used to pass the request to the next filter in the chain.
    }
}
