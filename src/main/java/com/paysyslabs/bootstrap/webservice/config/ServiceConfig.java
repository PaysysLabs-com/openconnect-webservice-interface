package com.paysyslabs.bootstrap.webservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paysyslabs.bootstrap.webservice.entities.WSParamDetail;
import com.paysyslabs.bootstrap.webservice.models.TransactionDetails;
import com.paysyslabs.bootstrap.webservice.repo.WSParamDetailRepository;
import com.paysyslabs.bootstrap.webservice.utils.RecoveringChannelListener;
import com.paysyslabs.bootstrap.webservice.utils.RecoveringRpcClient;
import com.rabbitmq.client.Connection;

@Configuration
public class ServiceConfig {
    
    @Autowired
    private WSParamDetailRepository paramDetailRepository;
    
    @Autowired
    private Connection queueConnection;

    @Value("${queue.config.rpc.queue}")
    private String queue;
    
    @Value("${queue.config.rpc.timeout}")
    private Integer timeout;

    @Autowired
    private RecoveringChannelListener recoveringChannelListener;
    
    @Bean("transactionMap")
    public Map<String, TransactionDetails> prepareTransactionMap() {
        Map<String, TransactionDetails> map = new HashMap<>();
        
        for (WSParamDetail detail : paramDetailRepository.findAll()) {
            map.put(detail.getTransactionType(), detail.getTransactionDetail());
        }
        
        return map;
    }

    @Bean("rpcClient")
    public RecoveringRpcClient client() throws Exception {
        RecoveringRpcClient client = new RecoveringRpcClient(queueConnection.createChannel(), "", queue, timeout);
        recoveringChannelListener.setClient(client);
        return client;
    }
    
}
