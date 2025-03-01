package com.emmanuel.microservices.limits_service.controller;

import com.emmanuel.microservices.limits_service.configuration.Configuration;
import com.emmanuel.microservices.limits_service.controller.bean.Limits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//This annotation is used to create RESTful web services using Spring MVC
public class LimitsController {

    @Autowired //This annotation is used to inject the Configuration bean into this class
    private Configuration configuration;

    @GetMapping("/limits")//This annotation is used to map HTTP GET requests onto specific handler methods
    public Limits retrieveLimits(){
        return new Limits(configuration.getMinimum(), configuration.getMaximum());
    }
}
