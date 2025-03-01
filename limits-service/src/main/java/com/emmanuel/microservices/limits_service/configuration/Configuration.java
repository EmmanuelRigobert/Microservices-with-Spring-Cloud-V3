package com.emmanuel.microservices.limits_service.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component //This annotation is used to indicate that the class is a Spring bean
@ConfigurationProperties("limits-service") //This annotation is used to bind the properties defined in the application.properties file to the fields of this class
public class Configuration {

    private int minimum;
    private int maximum;

    public int getMinimum() {
        return minimum;
    }

    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}
