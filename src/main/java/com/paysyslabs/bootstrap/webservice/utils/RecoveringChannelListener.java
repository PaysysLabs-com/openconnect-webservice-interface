package com.paysyslabs.bootstrap.webservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import net.jodah.lyra.event.DefaultChannelListener;

@Component
public class RecoveringChannelListener extends DefaultChannelListener {
    
    private static final Logger LOG = LoggerFactory.getLogger(RecoveringChannelListener.class);
    
    private RecoveringRpcClient client;
    
    @Override
    public void onRecovery(Channel channel) {
        LOG.info("trying to restart consumer on client {}", client);
        if (client != null)
            client.setup(channel);
    }
    
    @Override
    public void onRecoveryFailure(Channel channel, Throwable failure) {
        LOG.error("onRecoveryFailure OOPS", failure);
    }

    public void setClient(RecoveringRpcClient client) {
        this.client = client;
    }    
}
