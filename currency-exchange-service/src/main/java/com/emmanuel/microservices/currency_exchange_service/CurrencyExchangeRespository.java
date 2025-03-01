package com.emmanuel.microservices.currency_exchange_service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyExchangeRespository extends JpaRepository<CurrencyExchange, Long> {
    // This interface extends the JpaRepository interface which is used to perform CRUD operations on the CurrencyExchange entity such as save, findAll, findById, delete, etc
    // The JpaRepository interface takes two parameters: the entity class which is mapped to database table and the datatype of primary key type

    CurrencyExchange findByFromAndTo(String from, String to);

}
