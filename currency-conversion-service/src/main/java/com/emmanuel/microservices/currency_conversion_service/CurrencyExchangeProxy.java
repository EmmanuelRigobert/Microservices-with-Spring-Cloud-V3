package com.emmanuel.microservices.currency_conversion_service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(name = "currency-exchange", url = "localhost:8000") //This annotation is used to specify the name of the service to which the request will be made and the URL of the service
public interface CurrencyExchangeProxy {

    @GetMapping("/currency-exchange/from/{from}/to/{to}")//This annotation is used to map HTTP GET requests onto specific handler methods
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);//This method is used to get the exchange value from the currency-exchange-service based on the from and to values

}
