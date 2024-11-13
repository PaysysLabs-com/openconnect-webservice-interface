package com.paysyslabs.bootstrap.webservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paysyslabs.queue.QueueConfig;
import com.rabbitmq.client.Connection;

@Configuration
public class QueueConnectionInitializationConfiguration {

    @Autowired
    private QueueConfig queueConfig;
    
    @Bean
    public Connection queueConnection() throws Exception {
        return queueConfig.createConnection();
    }
}
