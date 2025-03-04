package com.emmanuel.microservices.currency_conversion_service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//When the currency conversion calls the currency exchange service via resttemplate, we want to be able to trace the call using micrometer and see the metrics in zipkin
@Configuration(proxyBeanMethods = false) //This annotation is used to indicate that this class is a configuration class. The proxyBeanMethods attribute is set to false to avoid creating CGLIB proxies for @Bean methods
    public class RestTemplateConfiguration {

        @Bean
        RestTemplate restTemplate(RestTemplateBuilder builder) {//This method is used to create a RestTemplate bean
            return builder.build();//This returns a RestTemplate object created using the RestTemplateBuilder
        }
    }
