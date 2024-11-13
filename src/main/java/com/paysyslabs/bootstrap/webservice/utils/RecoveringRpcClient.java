package com.paysyslabs.bootstrap.webservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paysyslabs.queue.QueueUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.RpcClient;

public class RecoveringRpcClient {
    
    private static final Logger LOG = LoggerFactory.getLogger(RecoveringRpcClient.class);

    private RpcClient client;
    
    private final String exchange;
    private final String routingKey;
    private final int timeout;
    
    private Channel channel;

    public RecoveringRpcClient(Channel channel, String exchange, String routingKey, int timeout) throws Exception {
        super();
        this.exchange = exchange;
        this.routingKey = routingKey;
        this.timeout = timeout;
        
        this.setup(channel);
    }
    
    public void setup(Channel channel) {
        this.channel = channel;
        
        LOG.info("setting up RpcClient on {}", channel);
        try {
            this.client = new RpcClient(channel, exchange, routingKey, timeout);
        } catch (Exception e) {
            LOG.error("setup OOPS", e);
        }
    }
    
    public String call(String message) throws Exception {
        if (client == null)
            throw new Exception("RpcClient not initialized");
        
        if (!QueueUtils.isBeingConsumed(channel, routingKey))
            throw new Exception("QueueProcessor is down");
        
        return client.stringCall(message);
    }

}
