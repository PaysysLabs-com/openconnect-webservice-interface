package com.paysyslabs.bootstrap.webservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

public class PrettyPrinter {

    private static final Logger LOG = LoggerFactory.getLogger(PrettyPrinter.class);

    @Async
    public void print(long requestNumber, String result) throws Exception {
        LOG.info("\n\n[ RESPONSE #{} ]\n\n{}\n\n", requestNumber, XMLBeautifier.format(result));
    }
    
}
