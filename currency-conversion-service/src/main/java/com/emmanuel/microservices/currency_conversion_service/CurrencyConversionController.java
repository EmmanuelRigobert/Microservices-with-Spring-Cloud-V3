package com.emmanuel.microservices.currency_conversion_service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
public class CurrencyConversionController {

    @Autowired //This annotation is used to inject the CurrencyExchangeProxy bean into this class
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplateConfiguration restTemplate;

    @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversionFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){

        CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);//This method is used to get the exchange value from the currency-exchange-service based on the from and to values

        return new CurrencyConversion(currencyConversion.getId(),from,to, quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " feign");
    }

    //Below is an alternative to the FeignClient annotation. This makes use of the RestTemplate class to make RESTful API calls to the currency-exchange-service
    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    public CurrencyConversion calculateCurrencyConversion(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity){
        HashMap<String, String> uriVariables = new HashMap<>();//This is a HashMap object that stores the from and to values
        uriVariables.put("from", from);
        uriVariables.put("to", to);

        //ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables); //This method is used to get the response from the currency-exchange-service based on the from and to values. The response will be converted to the CurrencyConversion class.
        ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class, uriVariables); //This method is used to get the response from the currency-exchange-service based on the from and to values. The response will be converted to the CurrencyConversion class. Here we use the restemplate object created in the RestTemplateMicrometer. This allows us to trace the call using micrometer and see the metrics in zipkin.
        //The above method won't work when application is run from an image in a container. The URL will be localhost:8000 which is not the correct URL. We need to use the service name instead of localhost.

        CurrencyConversion currencyConversion = responseEntity.getBody();//This method is used to get the body of the response from the currency-exchange-service and store it in the currencyConversion object

        return new CurrencyConversion(currencyConversion.getId(),from,to, quantity,currencyConversion.getConversionMultiple(),quantity.multiply(currencyConversion.getConversionMultiple()), currencyConversion.getEnvironment() + " rest template");
    }
}
