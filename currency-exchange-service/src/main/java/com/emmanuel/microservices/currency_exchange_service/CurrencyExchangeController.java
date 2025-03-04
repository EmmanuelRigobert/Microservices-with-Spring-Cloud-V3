package com.emmanuel.microservices.currency_exchange_service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CurrencyExchangeController {

    private Logger logger = LoggerFactory.getLogger(CurrencyExchangeController.class); //This is used to log messages

    @Autowired//This annotation is used to inject the CurrencyExchangeRespository bean into this class
    private CurrencyExchangeRespository repository;

    @Autowired//This annotation is used to inject the Environment bean into this class
    private Environment environment;//This class is used to get the properties defined in the application.properties file

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyExchange retrieveExchangeValue(@PathVariable String from, @PathVariable String to){

        logger.info("retrieveExchangeValue called with {} to {}", from, to);//This logs the message "retrieveExchangeValue called with {from} to {to}" values. To get these values, micrometer assigns a unique ID to each request and logs the request details.

//        CurrencyExchange currencyExchange = new CurrencyExchange(1000L, from, to, BigDecimal.valueOf(65));
        CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);//This method is used to get the exchange value from the database based on the from and to values
        if(currencyExchange == null){ //This condition checks if the entered from and to values are not found in the database
            throw new RuntimeException("Unable to find data for " + from + " to " + to);
        }
        String port = environment.getProperty("local.server.port"); //This method is used to get the value of the server port from the application.properties file
        currencyExchange.setEnvironment(port); //This method is used to set the value of the server port to the environment field of the CurrencyExchange class
        return currencyExchange;
    }
}
