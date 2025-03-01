package com.emmanuel.microservices.currency_exchange_service;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity //This class is an entity class and it is used to create a table in the database
public class CurrencyExchange {

    @Id //This annotation is used to specify the primary key of the table
    private Long id;

    @Column(name = "currency_from") //This annotation is used to specify the column name in the database. Error in creation of table name 'from' because 'from' is a reserved keyword in database
    private String from;

    @Column(name = "currency_to") //This annotation is used to specify the column name in the database
    private String to;
    private BigDecimal conversionMultiple;
    private String environment;

    public CurrencyExchange() {
    }

    public CurrencyExchange(Long id, String from, String to, BigDecimal conversionMultiple) {
        super();
        this.id = id;
        this.from = from;
        this.to = to;
        this.conversionMultiple = conversionMultiple;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public BigDecimal getConversionMultiple() {
        return conversionMultiple;
    }

    public void setConversionMultiple(BigDecimal conversionMultiple) {
        this.conversionMultiple = conversionMultiple;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
