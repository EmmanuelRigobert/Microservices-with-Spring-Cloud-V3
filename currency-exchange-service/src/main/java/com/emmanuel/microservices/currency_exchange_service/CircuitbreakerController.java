package com.emmanuel.microservices.currency_exchange_service;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitbreakerController {

    private Logger logger = LoggerFactory.getLogger(CircuitbreakerController.class); //Here we are creating a logger object to log messages

    @GetMapping("/sample-api")
    //@Retry(name = "default")//The default retry configuration will be used to retry the sampleApi method 3 times
    //@Retry(name = "sample-api", fallbackMethod = "hardcodedResponse")//The sample-api retry configuration will be used to retry the sampleApi method. This configuration is defined in the application.properties file. The hardcodedResponse method will be called if the retry fails

    //@CircuitBreaker(name = "default", fallbackMethod = "hardcodedResponse")//The default circuit breaker configuration will be used to handle the sampleApi method. The hardcodedResponse method will be called if the circuit breaker fails
    //After repeated failures, the circuit breaker will return a fallback response instead of trying to execute the operation.
    //The Circuit Breaker has 3 states: CLOSED, OPEN, and HALF_OPEN. The default state is CLOSED (The service is being called repeatedly). When the number of failures exceeds the threshold, the state changes to OPEN(The circuit breaker stops the service from being called repeatedly while sending hardcoded response). After a specified time, the state changes to HALF_OPEN, allowing a limited number of requests to pass through. If those requests are successful, the state changes back to CLOSED. If they fail, the state changes back to OPEN. Thresholds and time intervals can be configured in the application.properties file.

    //@RateLimiter(name = "default")//The default rate limiter configuration will be used to limit the number of requests to the sampleApi method. e.g 100 requests per 10 seconds

    @Bulkhead(name = "default")//The default bulkhead configuration will be used to limit the number of concurrent requests to the sampleApi method. e.g 10 concurrent requests
    public String sampleApi(){
        logger.info("Sample API call received");//This logs the message "Sample API call received"
        ResponseEntity<String> forEntity = new  RestTemplate().getForEntity("http://localhost:8000/some-dummy-url", String.class);//This creates a new RestTemplate object and makes a GET request to the URL specified. The response will be converted to a String object and stored in the forEntity object
        return forEntity.getBody();//This returns the body of the response
    }

    public String hardcodedResponse(Exception ex){//This method is called if the retry fails. The Exception object is passed as a parameter to enable the method to handle the exception thrown by the retry
        return "fallback-response";//This method is called if the retry fails. It returns the message "fallback-response"
    }
}
